package com.example.itubeapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper databaseHelper;
    private static final int DATABASE_VERSION = 1;
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_PLAYLIST = "playlist";


    private static final String DATABASE_NAME = "iTube";

    private static final String DATABASE_TABLE_NAME1 = "userinfodb";
    private static final String DATABASE_TABLE_NAME2 = "playlistdb";

    private Context context;


    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (databaseHelper == null) {
            databaseHelper = new DatabaseHelper(context);
        }
        return databaseHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Create tables SQL execution
        String CREATE_INFO_TABLE = "CREATE TABLE " + DATABASE_TABLE_NAME1 + "(" + COLUMN_NAME + " " + "TEXT, " + COLUMN_USERNAME + " TEXT, " + COLUMN_PASSWORD + " TEXT " + ")";
        String CREATE_PLAYLIST_TABLE = "CREATE TABLE " + DATABASE_TABLE_NAME2 + "(" + COLUMN_PLAYLIST + " TEXT " + ")";


        Log.d("Table create SQL: ", "" + CREATE_INFO_TABLE);
        Log.d("Table create SQL1: ", "" + CREATE_PLAYLIST_TABLE);


        db.execSQL(CREATE_INFO_TABLE);
        db.execSQL(CREATE_PLAYLIST_TABLE);

        Log.d("DB created!", "");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);

        // Create tables again
        onCreate(db);
    }

    public long insertUserItem(UserItem item) {

        long id = -1;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, item.getName());
        contentValues.put(COLUMN_USERNAME, item.getUsername());
        contentValues.put(COLUMN_PASSWORD, item.getPassword());

        try {
            id = sqLiteDatabase.insertOrThrow(DATABASE_TABLE_NAME1, null, contentValues);
        } catch (SQLiteException e) {
            Log.d("Exception: ", "" + e.getMessage());
            Toast.makeText(context, "Operation failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

        return id;
    }

    public List<UserItem> getUserItems() {

        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        Cursor cursor = null;
        try {

            cursor = sqLiteDatabase.query(DATABASE_TABLE_NAME1, null, null, null, null, null, null, null);

            if (cursor != null)
                if (cursor.moveToFirst()) {
                    List<UserItem> itemList = new ArrayList<>();
                    do {
                        String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                        String username = cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME));
                        String password = cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD));

                        UserItem item = new UserItem();
                        item.setName(name);
                        item.setUsername(username);
                        item.setPassword(password);

                        itemList.add(item);
                    } while (cursor.moveToNext());

                    return itemList;
                }
        } catch (Exception e) {
            Log.d("Exception: ", "" + e.getMessage());
            Toast.makeText(context, "Operation failed", Toast.LENGTH_SHORT).show();
        } finally {
            if (cursor != null)
                cursor.close();
            sqLiteDatabase.close();
        }
        return Collections.emptyList();
    }

    public long insertPlaylistItem(String item) {

        long id = -1;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_PLAYLIST, item);

        try {
            id = sqLiteDatabase.insertOrThrow(DATABASE_TABLE_NAME2, null, contentValues);
        } catch (SQLiteException e) {
            Log.d("Exception: ", "" + e.getMessage());
            Toast.makeText(context, "Operation failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

        return id;
    }

    public List<String> getPlayListItems() {

        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        Cursor cursor = null;
        try {

            cursor = sqLiteDatabase.query(DATABASE_TABLE_NAME2, null, null, null, null, null, null, null);

            if (cursor != null)
                if (cursor.moveToFirst()) {
                    List<String> itemList = new ArrayList<>();
                    do {
                        String plist = cursor.getString(cursor.getColumnIndex(COLUMN_PLAYLIST));
                        itemList.add(plist);
                    } while (cursor.moveToNext());

                    return itemList;
                }
        } catch (Exception e) {
            Log.d("Exception: ", "" + e.getMessage());
            Toast.makeText(context, "Operation failed", Toast.LENGTH_SHORT).show();
        } finally {
            if (cursor != null)
                cursor.close();
            sqLiteDatabase.close();
        }
        return Collections.emptyList();
    }

}