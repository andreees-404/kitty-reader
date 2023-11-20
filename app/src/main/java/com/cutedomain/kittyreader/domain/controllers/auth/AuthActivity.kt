package com.cutedomain.kittyreader.domain.controllers.auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cutedomain.kittyreader.views.MainActivity
import com.facebook.CallbackManager
import com.google.firebase.auth.FirebaseAuth

class AuthActivity : AppCompatActivity() {

    private val callbackManager = CallbackManager.Factory.create()
    private val GOOGLE_SIGN_IN = 100
    private val TAG = "AUTH_ACTIVITY"
    private val mAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    @Override
    override fun onStart() {
        super.onStart()

        if (mAuth.currentUser != null){
            goHome()
            finish()
        }

    }



    /*
    * Autenticar mediante Facebook OAuth2
    *
    * */
    //internal fun facebookSignIn(){
    //    // Obtener el email
    //    LoginManager.getInstance().logInWithReadPermissions(this, listOf("email"))

    //    // Autenticar el usuario
    //    LoginManager.getInstance().registerCallback(callbackManager, object : FacebookCallback<LoginResult>{
    //        override fun onSuccess(result: LoginResult?) {
    //            result?.let {
    //                val token = it.accessToken

    //                val credential = FacebookAuthProvider.getCredential(token.token)
    //                FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener{
    //                    if (it.isSuccessful){
    //                        goHome()
    //                    }
    //                    else {

    //                    }
    //                }
    //            }
    //        }

    //        override fun onCancel() {
    //            Log.d(TAG, "onCancel: El usuario ha cancelado la operación!")
    //        }

    //        override fun onError(error: FacebookException?) {
    //            TODO("Not yet implemented")
    //        }
    //    })
    //}




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

}

