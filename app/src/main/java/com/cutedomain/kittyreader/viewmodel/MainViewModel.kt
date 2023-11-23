package com.cutedomain.kittyreader.viewmodel

import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {

    val visiblePermissionDialog = mutableListOf<String>()


    /* Cerrar el dialogo
    * */
    fun dismissDialog(){
        visiblePermissionDialog.removeLast()
    }

    fun onPermissionResult(
        permission: String,
        isGranted: Boolean
    ){
        if (!isGranted){
            visiblePermissionDialog.add(0, permission)
        }
    }
}