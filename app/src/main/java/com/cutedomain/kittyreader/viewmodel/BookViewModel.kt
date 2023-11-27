package com.cutedomain.kittyreader.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cutedomain.kittyreader.domain.controllers.database.BooksDatabase
import com.cutedomain.kittyreader.models.EBook

class BookViewModel(context: Context){
    private val dbHelper = BooksDatabase(context)
    private val _libros = MutableLiveData<List<EBook>>()

    val books: LiveData<List<EBook>> get() = _libros

    init {
        loadBooks()
    }

    @SuppressLint("Range")
    private fun loadBooks() {
        val cursor = dbHelper.allBooks 
        val bookList = mutableListOf<EBook>()

        if (cursor != null && cursor.moveToFirst()) {
            do {
                val isbn = cursor.getString(cursor.getColumnIndex(BooksDatabase.BooksEntry.id_books))
                val title = cursor.getString(cursor.getColumnIndex(BooksDatabase.BooksEntry.title_col))
                val author = cursor.getString(cursor.getColumnIndex(BooksDatabase.BooksEntry.author_col))
                val url = cursor.getString(cursor.getColumnIndex(BooksDatabase.BooksEntry.url_col))
                val page = cursor.getInt(cursor.getColumnIndex(BooksDatabase.BooksEntry.page_col))
                val format = cursor.getString(cursor.getColumnIndex(BooksDatabase.BooksEntry.format_col))
            } while (cursor.moveToNext())
            
        }

    }
}