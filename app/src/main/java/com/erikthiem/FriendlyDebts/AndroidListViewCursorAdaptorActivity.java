package com.erikthiem.FriendlyDebts;


import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class AndroidListViewCursorAdaptorActivity extends Activity {

    private DebtsDbAdapter dbHelper;
    private SimpleCursorAdapter dataAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        dbHelper = new DebtsDbAdapter(this);
        dbHelper.open();

        //Clean all data
        dbHelper.deleteAllDebts();
        //Add some data
        dbHelper.insertSomeDebts();

        //Generate ListView from SQLite Database
        displayListView();

    }

    private void displayListView() {


        Cursor cursor = dbHelper.fetchAllDebts();

        // The desired columns to be bound
        String[] columns = new String[] {
                DebtsDbAdapter.KEY_AMOUNT,
                DebtsDbAdapter.KEY_NAME,
                DebtsDbAdapter.KEY_DATE,
                DebtsDbAdapter.KEY_DESCRIPTION
        };

        // the XML defined views which the data will be bound to
        int[] to = new int[] {
                R.id.amount,
                R.id.name,
                R.id.date,
                R.id.description,
        };

        // create the adapter using the cursor pointing to the desired data
        //as well as the layout information
        dataAdapter = new SimpleCursorAdapter(
                this, R.layout.country_info,
                cursor,
                columns,
                to,
                0);

        ListView listView = (ListView) findViewById(R.id.listView1);
        // Assign adapter to ListView
        listView.setAdapter(dataAdapter);

    }
}