package com.cutedomain.kittyreader.domain.controllers

import android.content.Context
import android.widget.Toast
import com.cutedomain.kittyreader.screens.account.ShowErr
import com.google.firebase.auth.FirebaseAuth

class UserController {

    // Esta clase define las validaciones
    // y operaciones necesarias para con los usuarios
    /*
    * Esta función implementa la lógica de inicio de sesión a la app
    * @args String email
    * @args String pass
    * @args Context context
    * @args NavController navController
    * @args ViewModel userViewModel
    *
    * */


    // Función para iniciar sesión con email
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


    // Función para registrar nuevos usuarios
    internal fun SignUp(
        email: String,
        pass: String,
        context: Context,
        conditionalTerms: Boolean
    ): Boolean {
        var register: Boolean = false
        if (conditionalTerms) {
            if (email.isNotEmpty() && pass.isNotEmpty() && verifyEmail(email)) {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, pass)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            Toast.makeText(context, "Usuario creado con éxito", Toast.LENGTH_SHORT).show()
                            register = true
                        } else {
                            ShowErr(context, "El usuario o contraseña son inválidos, vuelve a intentarlo.")
                        }
                    }
                if (register) return true
            }
        } else { register = false }
        return false
    }

    // Función para validar email con expresiones regulares
    internal fun verifyEmail(email: String): Boolean{
        val regex = Regex("^[^@]+@[^@]+\\.[a-zA-Z]{2,}\$")
        return regex.matches(email)
    }
    internal fun checkPass(pass: String, chkPass: String) : Boolean{
        if (!pass.equals(chkPass) || ( pass.length > 8 || chkPass.length > 8)){
            return false
        }
        return true
    }

    // Login con Facebook
    internal fun SignInFacebook() {
        // Instancia de Firebase

    }

    // Login con Google
    internal fun SignInGoogle() {

    }


}