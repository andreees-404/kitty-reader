package com.cutedomain.kittyreader.domain.controllers

import android.content.Context
import android.widget.Toast
import androidx.navigation.NavController
import com.cutedomain.kittyreader.screens.account.ShowErr
import com.cutedomain.kittyreader.screens.navigation.AppScreens
import com.google.firebase.auth.FirebaseAuth

class UserController {

    // Esta clase define las validaciones
    // y operaciones necesarias para con los usuarios
    /*
    * Esta función implementa la lógica de
    * inicio de sesión a la app
    * @args String email
    * @args String pass
    * @args Context context
    * @args NavController navController
    * @args ViewModel userViewModel
    *
    * */
    // Login function -> Firebase
    internal fun SignIn(email: String, pass: String, context: Context, navController: NavController){
        if (email.isNotEmpty() && pass.isNotEmpty()){
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email,pass).
            addOnCompleteListener{
                if (it.isSuccessful){
                    navController.navigate(AppScreens.LibraryScreen.route)
                }else ShowErr(context, "El usuario ingresado no es válido, vuelve a intentarlo.")
            }
        }
        else{
            ShowErr(context, "Los campos no pueden estar vacíos, por favor intenta otra vez.")
        }
    }



    // SignUp Function -> Firebase
    internal fun SignUp(email: String, pass: String, context: Context, navController: NavController){
        if (email.isNotEmpty() && pass.isNotEmpty()){
            FirebaseAuth.getInstance().
            createUserWithEmailAndPassword(email,pass).addOnCompleteListener{
                if (it.isSuccessful) {
                    Toast.makeText(context, "Usuario creado con éxito", Toast.LENGTH_SHORT).show()
                    navController.navigate(AppScreens.LoginScreen.route)
                } else {
                    ShowErr(context, "El usuario o contraseña son inválidos, vuelve a intentarlo.")
                }
            }
        }
        else{
            ShowErr(context, "Los campos no pueden estar vacíos, por favor ingresa otra vez.")
        }
    }


}