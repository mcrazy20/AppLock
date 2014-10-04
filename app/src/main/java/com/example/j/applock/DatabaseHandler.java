package com.example.j.applock;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by { on 10/4/2014.
 */
public class DatabaseHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "protectedApps";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_APPS = "apps";
    private static final String KEY_ID = "id";
    private static final String KEY_APP = "app";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_APPS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_APP + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i2) {
// Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_APPS);

        // Create tables again
        onCreate(db);
    }

    public void addApp(String app) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_APP, app); // Contact Name

        // Inserting Row
        db.insert(TABLE_APPS, null, values);
        Log.e("added", app);
        db.close(); // Closing database connection
    }

    public void getApps() {
        //List<Contact> contactList = new ArrayList<Contact>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_APPS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                String app = cursor.getString(1);
                // Adding contact to list
                //contactList.add(contact);
                Log.e("test", app);
            } while (cursor.moveToNext());
        }

        // return contact list
        //return contactList;
    }

    public boolean isInDB(String app) {
        boolean result = false;
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT 'x' FROM " + TABLE_APPS + " WHERE " + KEY_APP + " = \"" + app + "\"";
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.getCount() > 0)
            result = true;
        return result;
    }

    public void delete(String app) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_APPS, KEY_APP + " = ?",
                new String[]{app});
        Log.e("deleted", app);
        db.close();
    }
}
