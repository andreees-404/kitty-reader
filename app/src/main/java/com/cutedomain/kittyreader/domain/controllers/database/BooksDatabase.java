package com.cutedomain.kittyreader.domain.controllers.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.cutedomain.kittyreader.models.Book;
import com.cutedomain.kittyreader.models.EBook;

import java.util.ArrayList;
import java.util.List;

public class BooksDatabase extends SQLiteOpenHelper {

    public static final String TAG = "BooksDatabase";
    // DBname
    public static final String DB_NAME = "kittyreader";

    // DBversion
    public static final int DB_VERSION = 1;


    public static class BooksEntry implements BaseColumns {
        public static final String books_table = "books";

        public static final String id_books = "book_id";
        public static final String title_col = "title";
        public static final String author_col = "author";
        public static final String url_col = "url";
        public static final String page_col = "current_page";
        public static String format_col = "format";
    }

    private static final String SQL_CREATE_BOOKS = "CREATE TABLE IF NOT EXISTS " + BooksEntry.books_table +"(" +
            BooksEntry.id_books + " TEXT PRIMARY KEY," +
            BooksEntry.title_col + " TEXT NOT NULL," +
            BooksEntry.author_col + " TEXT NOT NULL," +
            BooksEntry.url_col + " TEXT NOT NULL," +
            BooksEntry.page_col + " integer," +
            BooksEntry.format_col + " TEXT)";

    private static final String SQL_DELETE_BOOKS = "DROP TABLE IF EXISTS " + BooksEntry.books_table;


    private MutableLiveData<List<EBook>> _booksLiveData = new MutableLiveData();


    public MutableLiveData<List<EBook>> booksLiveData = _booksLiveData;

    public BooksDatabase(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }



    /*
    * */
    @Override
    public void onCreate(SQLiteDatabase db) {
        createBooksTable(db);

    }

    /* Crear la tabla libros
    * @param db Contexto de la base de datos en uso
    */
    private void createBooksTable(SQLiteDatabase db) {
        try {
            db.execSQL(SQL_CREATE_BOOKS);
            Log.d(TAG, "onCreate: Books table created successfully");
        } catch (SQLException e){
            Log.d(TAG, "onCreate: SQL not executed: " + e.getMessage());
        }
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Borramos la tabla para actualizar
        db.execSQL(SQL_DELETE_BOOKS);
        onCreate(db);
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
    public int addBook(String title, String author, String url, int page, String format){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        try {
            values.put(BooksEntry.title_col, title);
            values.put(BooksEntry.author_col, author);
            values.put(BooksEntry.url_col, url);
            values.put(BooksEntry.page_col, page);
            values.put(BooksEntry.format_col, format);

            db.insert(BooksEntry.books_table, null, values);
            Log.d(TAG, "addBook: Libro agregado ");
            return 1;
        } catch (Exception e){
            Log.d(TAG, "addBook: No se puede agregar el libro" + e.getMessage());
            return -1;
        }
    }


    public void deleteBook(String _isbn){
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(BooksEntry.books_table, BooksEntry.id_books + "=" + _isbn, null);
        } catch(SQLException e){
            Log.d(TAG, "deleteBook: No se puede eliminar el libro" + e.getMessage());
        }
    }


    /* Actualizar una página de un libro
    *
    * @param _isbn Identificador del libro
    * @param page Ultima página leída del libro
    *
    * @return Si la página fue actualizada
    */
    public boolean updatePage(String _isbn, int page){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        try {
            values.put(BooksEntry.page_col, page);
            int update_row = db.update(BooksEntry.books_table, values, BooksEntry.id_books + "=" + _isbn, null);
            db.close();
            notifyLiveData();
            return update_row > 0;
        } catch (Exception e){
            Log.d(TAG, "updatePage: " + e.getMessage());
            return false;
        }
    }


    /* Notificar los cambios realizados al liveData
    * */
    private void notifyLiveData(){
        List<EBook> updatedLibros = getBookList();
        _booksLiveData.postValue(updatedLibros);
    }


    /* Obtener la lista de libros y crear los modelos para asignarlos a la vista
    * */
    private List<EBook> getBookList() {
        Cursor cursor = getAllBooks();
        List<EBook> libros = new ArrayList<>();

        try{
        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String isbn = cursor.getString(cursor.getColumnIndex(BooksEntry.id_books));
                @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex(BooksEntry.title_col));
                @SuppressLint("Range") String author = cursor.getString(cursor.getColumnIndex(BooksEntry.author_col));
                @SuppressLint("Range") String url = cursor.getString(cursor.getColumnIndex(BooksEntry.title_col));
                @SuppressLint("Range") int page = cursor.getInt(cursor.getColumnIndex(BooksEntry.url_col));
                @SuppressLint("Range") String format = cursor.getString(cursor.getColumnIndex(BooksEntry.format_col));

                libros.add(new EBook(isbn, title, author, url, page, format));
            } while (cursor.moveToNext());
            cursor.close();

        }

        return libros;
    }catch (Exception e) {
            Log.d(TAG, "getBookList: No se puede obtener la lista de libros: " + e.getMessage());
            return libros;
        }
    }


    /* Obtener todos los registros de libro de la base de datos
    * */
    public Cursor getAllBooks() {
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            return db.query(BooksEntry.books_table, new String[] {
                    BooksEntry.id_books,
                    BooksEntry.title_col,
                    BooksEntry.author_col,
                    BooksEntry.url_col,
                    BooksEntry.format_col
            }, null, null, null, null, null);

        } catch (Exception e){
            Log.d(TAG, "getAllBooks: " + e.getMessage());
            return null;
        }
    }


}

