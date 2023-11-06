package com.cutedomain.kittyreader.models


data class EBook(
    val isbn : String,
    val title: String,
    val author: String,
    val date: String,
    val category: String,
    val image: Int
)
