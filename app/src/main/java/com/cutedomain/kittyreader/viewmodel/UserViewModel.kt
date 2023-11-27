package com.cutedomain.kittyreader.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserViewModel : ViewModel() {

    private val _email = MutableLiveData<String>()

    val email: LiveData<String> get() = _email


    fun setUserCredentials(email: String) {
        _email.value = email
    }


}