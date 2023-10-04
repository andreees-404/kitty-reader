package com.cutedomain.kittyreader.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserViewModel : ViewModel() {
    // Implement later
    private val _email= MutableLiveData<String>()
    private val _pass= MutableLiveData<String>()
    val email : LiveData<String> = _email
    val pass : LiveData<String> = _pass
}