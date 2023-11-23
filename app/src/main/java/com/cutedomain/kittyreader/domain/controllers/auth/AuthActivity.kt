package com.cutedomain.kittyreader.domain.controllers.auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cutedomain.kittyreader.views.MainActivity
import com.google.firebase.auth.FirebaseAuth

class AuthActivity : AppCompatActivity() {

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
        super.onActivityResult(requestCode, resultCode, data)



    }

}

