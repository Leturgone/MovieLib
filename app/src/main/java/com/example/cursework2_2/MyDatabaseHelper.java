package com.example.cursework2_2;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private Context context;
    private static String DATABASE_PATH; // полный путь к базе данных
    private static final String DATABASE_NAME = "moviebase.db"; //название бд
    private static final int DATABASE_VERSION = 1; //версия бд

    private static final String TABLE_NAME = "movie_libary"; // название таблицы в бд
    // названия столбцов
    private static final String COLUMN_ID ="_id";
    private static final String COLUMN_TITLE ="movie_title";
    private static final String COLUMN_DIRECTOR ="movie_director";
    private static final String COLUMN_YEAR ="movie_year";
    private static final String COLUMN_DESCRIPTION ="movie_description";
    private static final String COLUMN_POSTER ="movie_poster";
    private static  final String COLUMN_LEGTH = "movie_length";

    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        DATABASE_PATH = context.getApplicationInfo().dataDir + "/databases/" + DATABASE_NAME;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        String query = "CREATE TABLE " + TABLE_NAME +
//                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                COLUMN_TITLE + " TEXT, "+
//                COLUMN_DIRECTOR + " TEXT, "+
//                COLUMN_YEAR + " TEXT, "+
//                COLUMN_DESCRIPTION + " TEXT, "+
//                COLUMN_POSTER + " BLOB, "+
//                COLUMN_LEGTH+ " TEXT);";
//        db.execSQL(query);

    }

    public  void create_db(){
        File file = new File(DATABASE_PATH);
        if (!file.exists()) {
            //получаем локальную бд как поток
            try(InputStream myInput = context.getAssets().open(DATABASE_NAME);
                // Открываем пустую бд
                OutputStream myOutput = new FileOutputStream(DATABASE_PATH)) {

                // побайтово копируем данные
                byte[] buffer = new byte[1024];
                int length;
                while ((length = myInput.read(buffer)) > 0) {
                    myOutput.write(buffer, 0, length);
                }
                myOutput.flush();
            }
            catch(IOException ex){
                Log.d("DatabaseHelper", ex.getMessage());
            }
        }
    }
    public SQLiteDatabase open()throws SQLException {

        return SQLiteDatabase.openDatabase(DATABASE_PATH, null, SQLiteDatabase.OPEN_READWRITE);
    }




    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME); //удаляет таблицу
        onCreate(db);
    }
}
