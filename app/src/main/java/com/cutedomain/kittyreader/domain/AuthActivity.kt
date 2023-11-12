package com.cutedomain.kittyreader.domain

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.cutedomain.kittyreader.MainActivity
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth

class AuthActivity : AppCompatActivity() {

    private val callbackManager = CallbackManager.Factory.create()
    private val GOOGLE_SIGN_IN = 100
    private val TAG = "AUTH_ACTIVITY"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    /*
    * Autenticar mediante Facebook OAuth2
    *
    * */
    internal fun facebookSignIn(){
        // Obtener el email
        LoginManager.getInstance().logInWithReadPermissions(this, listOf("email"))

        // Autenticar el usuario
        LoginManager.getInstance().registerCallback(callbackManager, object : FacebookCallback<LoginResult>{
            override fun onSuccess(result: LoginResult?) {
                result?.let {
                    val token = it.accessToken

                    val credential = FacebookAuthProvider.getCredential(token.token)
                    FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener{
                        if (it.isSuccessful){
                            goHome()
                        }
                        else {

                        }
                    }
                }
            }

            override fun onCancel() {
                Log.d(TAG, "onCancel: El usuario ha cancelado la operación!")
            }

            override fun onError(error: FacebookException?) {
                TODO("Not yet implemented")
            }
        })
    }


    /* Autenticar con el proveedor de Google*/
    internal fun googleSignIn(){

    }

    /*
    * Dirigir a la pantalla principal
    *
    * */
    private fun goHome() {
        Intent(this, MainActivity::class.java).also {
            startActivity(it)
        }
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)



    }

    internal fun removeUser(password: String) {
        val auth = FirebaseAuth.getInstance()
        val user = auth.currentUser

        if(user != null){
             val credential: AuthCredential? = user?.email?.let { EmailAuthProvider.getCredential(it, password) }

             user?.delete()?.addOnCompleteListener {
                 if (it.isSuccessful) {
                     Log.d(TAG, "removeUser: User deleted!")
                     goHome()
                     finish()
                 }
             }?.addOnFailureListener {
                 Log.d(TAG, "removeUser: User cant removed!")
                 goHome()
                 finish()

                 }
             }
    }
}

