package com.example.cursework2_2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME = "MovieBase.db"; //название бд
    private static final int DATABASE_VERSION = 1; //версия бд

    private static final String TABLE_NAME = "movie_libary"; // название таблицы в бд
    // названия столбцов
    private static final String COLUMN_ID ="_id";
    private static final String COLUMN_TITLE ="movie_title";
    private static final String COLUMN_DIRECTOR ="movie_director";
    private static final String COLUMN_YEAR ="movie_year";
    private static final String COLUMN_DESCRIPTION ="movie_description";
    private static final String COLUMN_POSTER ="movie_poster";

    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT, "+
                COLUMN_DIRECTOR + " TEXT, "+
                COLUMN_YEAR + " TEXT, "+
                COLUMN_DESCRIPTION + " TEXT, "+
                COLUMN_POSTER + " BLOB);";
        db.execSQL(query);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME); //удаляет таблицу
        onCreate(db);
    }
}
