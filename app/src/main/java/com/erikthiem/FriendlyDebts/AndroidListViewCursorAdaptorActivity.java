package com.erikthiem.FriendlyDebts;


import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class AndroidListViewCursorAdaptorActivity extends Activity {

    private DebtsDbAdapter dbHelper;
    private SimpleCursorAdapter myDebtsDataAdapter;
    private SimpleCursorAdapter yourDebtsDataAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Start the application at the main screen
        setContentView(R.layout.main);

        // Get a reference to the debts database
        dbHelper = new DebtsDbAdapter(this);
        dbHelper.open();

        // Remove all data from the debts database. Will remove this for production.
        dbHelper.deleteAllDebts();

        // Add some test data to the debts database.
        dbHelper.insertTestDebts();

        // Connect listViews for myDebts and yourDebts to their respective data
        generateListViews();

    }

    private void generateListViews() {

        // Create cursors for myDebts and yourDebts to get the data from the database
        Cursor myDebtsCursor = dbHelper.fetchAllMyDebts();
        Cursor yourDebtsCursor = dbHelper.fetchAllYourDebts();

        // The desired columns to be bound
        String[] columns = new String[] {
                DebtsDbAdapter.KEY_DEBTOR,
                DebtsDbAdapter.KEY_AMOUNT,
                DebtsDbAdapter.KEY_NAME,
                DebtsDbAdapter.KEY_DATE,
                DebtsDbAdapter.KEY_DESCRIPTION
        };

        // the XML defined views which the data will be bound to
        int[] to = new int[] {
                R.id.debtor,
                R.id.amount,
                R.id.name,
                R.id.date,
                R.id.description,
        };

        // Create the MyDebts cursor and link it up to the MyDebts data and the MyDebts listView
        setupMyDebtsCursor(myDebtsCursor, columns, to);


        // Create the YourDebts cursor and link it up to the MyDebts data and the MyDebts listView
        setupYourDebtsCursor(yourDebtsCursor, columns, to);

    }

    private void setupMyDebtsCursor(Cursor myDebtsCursor, String[] columns, int[] to)
    {
        // Create a data cursor adapter for myDebts
        myDebtsDataAdapter = new SimpleCursorAdapter(
                this, R.layout.debt_info,
                myDebtsCursor,
                columns,
                to,
                0);

        // Get a reference to the listView
        ListView myDebtsListView = (ListView) findViewById(R.id.I_owe_listView);

        // Assign adapter to ListView
        myDebtsListView.setAdapter(myDebtsDataAdapter);
    }

    private void setupYourDebtsCursor(Cursor yourDebtsCursor, String[] columns, int[] to)
    {
        // Create a data cursor adapter for yourDebts
        yourDebtsDataAdapter = new SimpleCursorAdapter(
                this, R.layout.debt_info,
                yourDebtsCursor,
                columns,
                to,
                0);

        // Get a reference to the listView
        ListView yourDebtsListView = (ListView) findViewById(R.id.you_owe_listView);

        // Assign adapter to ListView
        yourDebtsListView.setAdapter(yourDebtsDataAdapter);
    }
}

