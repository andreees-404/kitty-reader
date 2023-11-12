package com.cutedomain.kittyreader.domain.controllers

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.cutedomain.kittyreader.domain.AuthActivity
import com.cutedomain.kittyreader.domain.UserViewModel
import com.cutedomain.kittyreader.screens.account.ShowErr
import com.facebook.CallbackManager
import com.google.firebase.auth.FirebaseAuth


class UserController {



    companion object {
        val TAG: String = "USER_CONTROLLER"
    }


    private val auth = AuthActivity()
    private val callback = CallbackManager.Factory.create()
    private lateinit var db: DatabaseController
    private lateinit var user: UserViewModel

    /*
    * Login con Firebase
    *
    * @param email Correo electrónico del usuario
    * @param pass Contraseña del usuario
    * @param context Pantalla de inicio de sesión
    *
    * @return Si el usuario se autentica correctamente*/
    internal fun SignIn(
        email: String,
        pass: String,
        context: Context
    ) : Boolean {
        var login: Boolean = false
        if (email.isNotEmpty() && pass.isNotEmpty()) {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        login = true
                    } else ShowErr(
                        context,
                        "El usuario ingresado no es válido, vuelve a intentarlo."
                    )
                }
            if (login) return true

        } else {
            ShowErr(context, "Los campos no pueden estar vacíos, por favor intenta otra vez.")
            login = false
        }
        return false
    }


    /*
    * Registrar nuevos usuarios con firebase
    *
    * @param email
    *   Dirección de correo electrónico del usuario
    *
    * @param pass
    *   Contraseña creada por el usuario
    *
    * @param context
    *   Contexto de la pantalla actual
    *
    *
    */
    internal fun SignUp(
        email: String,
        pass: String,
        context: Context,
    ): Boolean {
            var register = false

            if (email.isNotEmpty() && pass.isNotEmpty() && verifyEmail(email)) {

                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, pass)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            Toast.makeText(context, "Usuario creado con éxito", Toast.LENGTH_SHORT).show()
                            register = true


                        } else {
                            ShowErr(context, "El usuario o contraseña son inválidos, vuelve a intentarlo.")
                            Log.d(TAG, "SignUp: Failed to create user")
                        }
                    }
                if (register) return true
            }

        return false
    }

    // Función para validar email con expresiones regulares
    internal fun verifyEmail(email: String): Boolean{
        val regex = Regex("^[^@]+@[^@]+\\.[a-zA-Z]{2,}\$")
        return regex.matches(email)
    }
    internal fun checkPass(pass: String, chkPass: String) : Boolean{
        return !(!pass.equals(chkPass) || ( pass.length > 8 || chkPass.length > 8) || ( pass.isEmpty() || chkPass.isEmpty()))
    }

    // Login con Facebook
    internal fun authFacebook() {
        auth.facebookSignIn()
    }

    // Login con Google
    internal fun authGoogle() {
        auth.googleSignIn()
    }
    internal fun validatePass(pass: String): Boolean {
        return !(pass.length > 8 || pass.isEmpty())
    }

    internal fun deleteUser(pass: String){
        auth.removeUser(pass)
    }


}