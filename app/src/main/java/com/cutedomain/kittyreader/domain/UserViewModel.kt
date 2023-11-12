package com.cutedomain.kittyreader.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserViewModel : ViewModel() {

    private val _username = MutableLiveData<String>()
    private val _email= MutableLiveData<String>()
    private val _pass= MutableLiveData<String>()

    val username: LiveData<String> = _username
    val email : LiveData<String> = _email
    val pass : LiveData<String> = _pass

    fun setUsername(username: String){
        _username.value = username
    }

    fun setEmail(email: String){
        _email.value = email
    }
}