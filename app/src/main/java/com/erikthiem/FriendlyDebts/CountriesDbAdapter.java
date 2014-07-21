package com.erikthiem.FriendlyDebts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class CountriesDbAdapter {

    public static final String KEY_ROWID = "_id";
    public static final String KEY_AMOUNT = "amount";
    public static final String KEY_NAME = "name";
    public static final String KEY_DATE = "continent";
    public static final String KEY_REGION = "region";

    private static final String TAG = "CountriesDbAdapter";
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    private static final String DATABASE_NAME = "World";
    private static final String SQLITE_TABLE = "Country";
    private static final int DATABASE_VERSION = 1;

    private final Context mCtx;

    private static final String DATABASE_CREATE =
            "CREATE TABLE if not exists " + SQLITE_TABLE + " (" +
                    KEY_ROWID + " integer PRIMARY KEY autoincrement," +
                    KEY_AMOUNT + "," +
                    KEY_NAME + "," +
                    KEY_DATE + "," +
                    KEY_REGION + "," +
                    " UNIQUE (" + KEY_AMOUNT +"));";

    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }


        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.w(TAG, DATABASE_CREATE);
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + SQLITE_TABLE);
            onCreate(db);
        }
    }

    public CountriesDbAdapter(Context ctx) {
        this.mCtx = ctx;
    }

    public CountriesDbAdapter open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        if (mDbHelper != null) {
            mDbHelper.close();
        }
    }

    public long createCountry(String amount, String name,
                              String date, String region) {

        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_AMOUNT, amount);
        initialValues.put(KEY_NAME, name);
        initialValues.put(KEY_DATE, date);
        initialValues.put(KEY_REGION, region);

        return mDb.insert(SQLITE_TABLE, null, initialValues);
    }

    public boolean deleteAllCountries() {

        int doneDelete = 0;
        doneDelete = mDb.delete(SQLITE_TABLE, null , null);
        Log.w(TAG, Integer.toString(doneDelete));
        return doneDelete > 0;

    }

    public Cursor fetchCountriesByName(String inputText) throws SQLException {
        Log.w(TAG, inputText);
        Cursor mCursor = null;
        if (inputText == null  ||  inputText.length () == 0)  {
            mCursor = mDb.query(SQLITE_TABLE, new String[] {KEY_ROWID,
                            KEY_AMOUNT, KEY_NAME, KEY_DATE, KEY_REGION},
                    null, null, null, null, null);

        }
        else {
            mCursor = mDb.query(true, SQLITE_TABLE, new String[] {KEY_ROWID,
                            KEY_AMOUNT, KEY_NAME, KEY_DATE, KEY_REGION},
                    KEY_NAME + " like '%" + inputText + "%'", null,
                    null, null, null, null);
        }
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;

    }

    public Cursor fetchAllCountries() {

        Cursor mCursor = mDb.query(SQLITE_TABLE, new String[] {KEY_ROWID,
                        KEY_AMOUNT, KEY_NAME, KEY_DATE, KEY_REGION},
                null, null, null, null, null);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public void insertSomeDebts() {

        createCountry("12.47","Aakash","1/1/2012","Southern and Central Asia");
        createCountry("13.47","Aakash","2/2/2012","Southern Europe");
        createCountry("14.47","Aakash","3/3/2012","Northern Africa");
        createCountry("15.47","Aakash","4/4/2012","Polynesia");
        createCountry("16.47","Aakash","5/5/2012","Southern Europe");
        createCountry("17.47","Aakash","6/6/2012","Central Africa");
        createCountry("18.47","Aakash","7/7/2012","Caribbean");

    }

}