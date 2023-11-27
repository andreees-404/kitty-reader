package com.cutedomain.kittyreader.models


data class EBook(
    val isbn : String,
    val title: String,
    val author: String,
    val url: String,
    val lastPage: Int,
    val format: String,
)
