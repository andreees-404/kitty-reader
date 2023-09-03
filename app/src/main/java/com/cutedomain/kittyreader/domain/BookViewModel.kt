package com.cutedomain.kittyreader.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BookViewModel : ViewModel() {

    private val _email= MutableLiveData<String>()
    val email : LiveData<String> = _email
}