package com.cutedomain.kittyreader.models

data class Book(
    val isbn : String,
    val title: String,
    val author: String,
    val date: String,
    val category: String,
    val price: Float= 0.0F
)