package com.cutedomain.kittyreader.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserViewModel : ViewModel() {

    private val _name = MutableLiveData<String>()
    private val _lastname = MutableLiveData<String>()
    private val _email= MutableLiveData<String>()
    private val _pass= MutableLiveData<String>()

    val name: LiveData<String> = _name
    val lastname: LiveData<String> = _lastname
    val email : LiveData<String> = _email
    val pass : LiveData<String> = _pass
}