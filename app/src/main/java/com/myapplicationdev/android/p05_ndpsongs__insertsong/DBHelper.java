package com.myapplicationdev.android.p05_ndpsongs__insertsong;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    //TODO Define the Database properties
    private static final String DATABASE_NAME = "Songs.db";
    private static final int DATABASE_VERSION = 1 ;
    private static final String TABLE_Song = "Song";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_SONG_TITLE= "title";
    private static final String COLUMN_SINGERS = "Singers";
    private static final String COLUMN_YEAR = "year";
    private static final String COLUMN_stars = "Stars";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME,
                null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // CREATE TABLE Note
        // (id INTEGER PRIMARY KEY AUTOINCREMENT, note_content TEXT, rating
        // INTEGER );
        String createSongTableSql = "CREATE TABLE " + TABLE_Song + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_SONG_TITLE + " TEXT, "
                + COLUMN_SINGERS + " TEXT," +
                COLUMN_YEAR + "INTEGER," +
                COLUMN_stars + "INTERGER ) ";


        db.execSQL(createSongTableSql);
        Log.i("info", "created tables");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Song);
        onCreate(db);
    }

    public void insertNote(String title, String singers,int year,int stars) {
        // Get an instance of the database for writing
        SQLiteDatabase db = this.getWritableDatabase();
        // We use ContentValues object to store the values for the db operation
        ContentValues values = new ContentValues();
        // Store the column name as key and the note content as value
        values.put(COLUMN_SONG_TITLE, title);
        // Store the column name as key and the rating as value
        values.put(COLUMN_SINGERS, singers);
        values.put(COLUMN_YEAR, year);
        values.put(COLUMN_stars, stars);
        // Insert the row into the TABLE_NOTE
        db.insert(TABLE_Song, null, values);
        // Close the database connection
        db.close();
    }
    public ArrayList<Song> getAllNotes() {
        ArrayList<Song> songs = new ArrayList<Song>();
        // "SELECT id, note_content, stars FROM note"
        String selectQuery = "SELECT " + COLUMN_ID + ","
                + COLUMN_SONG_TITLE + ","
                + COLUMN_SINGERS + ","
                + COLUMN_YEAR + ","
                + COLUMN_stars +
                " FROM " + TABLE_Song;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Loop through all rows and add to ArrayList
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String singers = cursor.getString(2);
                int years = cursor.getInt(3);
                int stars = cursor.getInt(4);

                Song song = new Song(id, title,singers,years, stars);
                songs.add(song);
            } while (cursor.moveToNext());
        }
        // Close connection
        cursor.close();
        db.close();
        return songs;
    }


}

