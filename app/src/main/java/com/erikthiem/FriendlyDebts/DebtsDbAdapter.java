package com.erikthiem.FriendlyDebts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DebtsDbAdapter {

    // Table Row Names
    public static final String KEY_ROWID = "_id";
    public static final String KEY_DEBTOR = "debtor";
    public static final String KEY_AMOUNT = "amount";
    public static final String KEY_NAME = "name";
    public static final String KEY_DATE = "date";
    public static final String KEY_DESCRIPTION = "description";

    // Debtor categories
    public static final String DEBTOR_I = "I";
    public static final String DEBTOR_YOU = "You";

    private static final String TAG = "DebtsDbAdapter";
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    private static final String DATABASE_NAME = "FriendlyDebts";
    private static final String SQLITE_TABLE = "DebtTable";
    private static final int DATABASE_VERSION = 1;

    private final Context mCtx;

    private static final String DATABASE_CREATE =
            "CREATE TABLE if not exists " + SQLITE_TABLE + " (" +
                    KEY_ROWID + " integer PRIMARY KEY autoincrement," +
                    KEY_DEBTOR + "," +
                    KEY_AMOUNT + "," +
                    KEY_NAME + "," +
                    KEY_DATE + "," +
                    KEY_DESCRIPTION + ");";

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

    public DebtsDbAdapter(Context ctx) {
        this.mCtx = ctx;
    }

    public DebtsDbAdapter open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        if (mDbHelper != null) {
            mDbHelper.close();
        }
    }

    public long createDebt(String debtor, String amount, String name,
                           String date, String description) {

        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_DEBTOR, debtor);
        initialValues.put(KEY_AMOUNT, amount);
        initialValues.put(KEY_NAME, name);
        initialValues.put(KEY_DATE, date);
        initialValues.put(KEY_DESCRIPTION, description);

        return mDb.insert(SQLITE_TABLE, null, initialValues);
    }

    public boolean deleteAllDebts() {

        int doneDelete = 0;
        doneDelete = mDb.delete(SQLITE_TABLE, null , null);
        Log.w(TAG, Integer.toString(doneDelete));
        return doneDelete > 0;

    }

    public Cursor fetchAllMyDebts() {

        Cursor myDebtsCursor = mDb.query(SQLITE_TABLE, new String[] {KEY_ROWID, KEY_DEBTOR,
                        KEY_AMOUNT, KEY_NAME, KEY_DATE, KEY_DESCRIPTION},
                KEY_DEBTOR + " = ?", new String[] { DEBTOR_I }, null, null, null);

        if (myDebtsCursor != null) {
            myDebtsCursor.moveToFirst();
        }

        return myDebtsCursor;
    }

    public Cursor fetchAllYourDebts() {

        Cursor yourDebtsCursor = mDb.query(SQLITE_TABLE, new String[] {KEY_ROWID, KEY_DEBTOR,
                        KEY_AMOUNT, KEY_NAME, KEY_DATE, KEY_DESCRIPTION},
                KEY_DEBTOR + " = ?", new String[] { DEBTOR_YOU }, null, null, null);

        if (yourDebtsCursor != null) {
            yourDebtsCursor.moveToFirst();
        }

        return yourDebtsCursor;
    }



    public void insertTestDebts() {

        createDebt(DEBTOR_I, "12.47", "Aakash", "1/1/2012", "Gas money ");
        createDebt(DEBTOR_YOU, "14.47", "Alex", "2/2/2012", "Food money");
        createDebt(DEBTOR_I, "14.47","Johns", "3/3/2012", "Gas money");
        createDebt(DEBTOR_YOU, "15.47", "Richard", "4/4/2012", "Food money");
        createDebt(DEBTOR_YOU, "16.47", "Aakash", "5/5/2012", "Gas money");
        createDebt(DEBTOR_YOU, "17.47", "Alex", "6/6/2012", "Food money");
        createDebt(DEBTOR_I, "14.47", "Aakash", "7/7/2012", "Drink money");

    }

}