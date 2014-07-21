package com.erikthiem.FriendlyDebts;


import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class AndroidListViewCursorAdaptorActivity extends Activity {

    private CountriesDbAdapter dbHelper;
    private SimpleCursorAdapter dataAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        dbHelper = new CountriesDbAdapter(this);
        dbHelper.open();

        //Clean all data
        dbHelper.deleteAllCountries();
        //Add some data
        dbHelper.insertSomeDebts();

        //Generate ListView from SQLite Database
        displayListView();

    }

    private void displayListView() {


        Cursor cursor = dbHelper.fetchAllCountries();

        // The desired columns to be bound
        String[] columns = new String[] {
                CountriesDbAdapter.KEY_AMOUNT,
                CountriesDbAdapter.KEY_NAME,
                CountriesDbAdapter.KEY_DATE,
                CountriesDbAdapter.KEY_REGION
        };

        // the XML defined views which the data will be bound to
        int[] to = new int[] {
                R.id.amount,
                R.id.name,
                R.id.date,
                R.id.region,
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