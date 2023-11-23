package com.cutedomain.kittyreader.domain.controllers.auth

import android.content.Context
import android.content.Intent
import android.util.Log
import com.cutedomain.kittyreader.viewmodel.UserViewModel
import com.cutedomain.kittyreader.views.MainActivity
import com.google.firebase.auth.FirebaseAuth


/* Administrar el acceso y sesión de usuario de Firebase mediante esta clase
*
*  @param context Pantalla a aplicar estas funciones
* */
class AuthManager(val context: Context) {

    private val TAG = "AuthManager"
    private val mAuth = FirebaseAuth.getInstance()
    private val userViewModel = UserViewModel()


    /* Login con Firebase
    *
    * @param email Correo electrónico del usuario
    * @param pass Contraseña del usuario
    *
    */
    internal fun signIn(email: String, pass: String){
        mAuth.signInWithEmailAndPassword(email.trim(), pass.trim()).addOnCompleteListener {
            if(it.isSuccessful){
                Log.d(TAG, "signIn: Login successfull!")
                goHome()
            }
        }.addOnFailureListener {
            Log.d(TAG, "signIn: login failed!")
        }
    }

    private fun goHome() {
        Intent(context, MainActivity::class.java).also {
            context.startActivity(it)
        }
    }
}