package com.cutedomain.kittyreader.domain.controllers.firebase

import android.content.Context
import com.cutedomain.kittyreader.domain.controllers.auth.AuthManager
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage


/*
* Administrar los archivos de Firebase Storage mediante el uso de esta clase
* @param context Pantalla en la cual aplicamos esta función
*/
class CloudStorageManager(context: Context) {
    private val storage = Firebase.storage
    private val reference = storage.reference
    private val authManager = AuthManager(context)
}
