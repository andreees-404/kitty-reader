package com.cutedomain.kittyreader.domain.controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.SegmentFinder;

import androidx.appcompat.app.AppCompatDialogFragment;

import org.w3c.dom.ls.LSParserFilter;

public class DatabaseController extends SQLiteOpenHelper {


    public static final String DB_NAME = "kittyreader";
    public static final int DB_VERSION = 1;

    public static final String table_name = "books";


    public static final String primary_key = "book_id";
    public static final String title_col = "title";
    public static final String author_col = "author";
    public static final String url_col = "url";
    public static final String category_col = "category";



    public DatabaseController(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }



    /*
    * */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE ";

        //db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public int addBook(String title, String author, String category, String url){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(title_col, title);
        values.put(author_col, author);
        values.put(url_col, url);
        values.put(category_col, category);

        db.insert(table_name, null, values);
        return 1;
    }
}
