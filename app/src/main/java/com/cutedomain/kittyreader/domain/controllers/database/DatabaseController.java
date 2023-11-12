package com.cutedomain.kittyreader.domain.controllers.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.SegmentFinder;
import android.util.Log;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.android.gms.common.api.internal.IStatusCallback;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.ls.LSParserFilter;

import java.util.Date;

public class DatabaseController extends SQLiteOpenHelper {

    public static final String TAG = "DatabaseController";
    public static final String DB_NAME = "kittyreader";
    public static final int DB_VERSION = 1;

    /* Table books
    * */
    public static final String books_table = "books";


    public static final String primary_key_books = "book_id";
    public static final String title_col = "title";
    public static final String author_col = "author";
    public static final String url_col = "url";
    public static final String category_col = "category";
    public static final String date_col = "category";
    private static final String year_col = "year";




    public DatabaseController(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }



    /*
    * */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String booksTableQuery = "CREATE TABLE " + books_table +"(" +
                primary_key_books + " integer primary key AUTOINCREMENT," +
                title_col + " TEXT," +
                author_col + " TEXT," +
                year_col + " TEXT," +
                category_col + " TEXT," +
                date_col +" TEXT)";


        try {
            db.execSQL(booksTableQuery);
            Log.d(TAG, "onCreate: Tables created successfully");
        } catch (SQLException e){
            Log.d(TAG, "onCreate: SQL not executed");
            e.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /*
    * Insert a book to database
    * ---------> Considerar si en un futuro se agrega el ISBN del libro?
    * @param title Titulo del libro
    * @param author Autor del libro
    * @param category Categoría del libro
    * @param url Dirección url de descarga del libro
    *
    * @retrun Si la consulta se hizo correctamente*/
    public int addBook(String title, String author, String category, String url, String year){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        try {
            values.put(title_col, title);
            values.put(author_col, author);
            values.put(url_col, url);
            values.put(category_col, category);
            values.put(year_col, year);

            db.insert(books_table, null, values);
            return 1;
        } catch (Exception e){
            e.printStackTrace();
            return -1;
        }
    }





    /*
    * Preguntar en la base de datos local por el usuario
    * */
    public void selectBooks() {
        SQLiteDatabase db = this.getReadableDatabase();

        try {

        } catch (SQLException exception){
            exception.printStackTrace();
        }
    }
}

