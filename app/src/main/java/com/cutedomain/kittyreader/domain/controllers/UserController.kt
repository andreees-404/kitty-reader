package com.cutedomain.kittyreader.domain.controllers

import android.content.Context
import android.content.Intent
import android.util.Log
import com.cutedomain.kittyreader.MainActivity
import com.cutedomain.kittyreader.domain.UserViewModel
import com.cutedomain.kittyreader.domain.controllers.database.DatabaseController
import com.google.firebase.auth.FirebaseAuth


class UserController {

    /* Necesario para el Logcat*/
    companion object {
        val TAG: String = "USER_CONTROLLER"
    }

    private val mAuth = FirebaseAuth.getInstance()
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
    internal fun signIn(email: String, password: String, context: Context){

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful){
                context.startActivity(Intent(context, MainActivity::class.java))
            }
            Log.d(TAG, "signIn: Logging...")
        }.addOnFailureListener {
            Log.d(TAG, "signIn: Login failed!")
        }.addOnCanceledListener {
            Log.d(TAG, "signIn: El usuario ha cancelado el login")
        }
    }

    
    
    /*
    * Registrar nuevos usuarios con firebase
    *
    * @param email Dirección de correo electrónico del usuario
    * @param pass Contraseña creada por el usuario
    *
    * @return Si el usuario ha sido registrado en Firebase
    *
    */
    internal fun signUp(email: String, password: String, context: Context){
        if (email.isNotEmpty() && password.isNotEmpty() && verifyEmail(email) && validatePass(password)) {
            mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful){
                        Log.d(TAG, "signUp: User Created")
                    } else {
                        Log.d(TAG, "signUp: User was not created!")
                    }
                }.addOnCanceledListener {
                    Log.d(TAG, "SignUp: El usuario ha cancelado la operación...")
                }
        }
    }



    /* Inspeccionar el email
    * 
    * @param email Correo electrónico del usuario
    * 
    * @return Si cumple los requisitos: tener @ y .<+2LETRAS>
    */
    internal fun verifyEmail(email: String): Boolean{
        val regex = Regex("^[^@]+@[^@]+\\.[a-zA-Z]{2,}\$")
        return regex.matches(email)
    }
    
    
    
    /*
    * Comprobar que las dos contraseñas sean iguales 
    * y que no estén vacías
    * 
    * @param pass Contraseña original
    * @param chkPass Confirmar la contraseña
    * 
    * @return Si son iguales
    */
    internal fun checkPass(pass: String, chkPass: String) : Boolean{
        return (pass == chkPass && (pass.isNotEmpty() && chkPass.isNotEmpty()))
    }



    /* Comprobar que la contraseña sea de mínimo 8 caracteres
    *  y que tenga al menos una mayúscula y un número
    * 
    * @param password Contraseña del usuario
    * 
    * @return Si cumple los requisitos
    * */
    internal fun validatePass(password: String): Boolean{
        if (password.trim().length >= 8) {
            val passwordRegex = Regex("^(?=.*[0-9])(?=.*[A-Z]).+\$")
            return passwordRegex.matches(password)
        }
        return false
    }


    
    /* Cerrar la sesíón actual
    * 
    * @param context Pantalla actual
    */
    internal fun signOut(context: Context) {
        if(mAuth.currentUser != null){
            mAuth.signOut()
            context.startActivity(Intent(context, MainActivity::class.java))
        }
    }

}