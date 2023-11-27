package com.cutedomain.kittyreader.domain.utils

import android.content.Context
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await


class UserUtils {

    /* Necesario para el Logcat*/
    companion object {
        val TAG: String = "USER_CONTROLLER"
    }

    private val mAuth: FirebaseAuth by lazy { Firebase.auth }

    /*
    * Login con Firebase
    *
    * @param email Correo electrónico del usuario
    * @param pass Contraseña del usuario
    * @param context Pantalla de inicio de sesión
    *
    * @return Si el usuario se autentica correctamente*/
    suspend fun signIn(email: String, password: String){
        try {
            val authResult = mAuth.signInWithEmailAndPassword(email, password).await()

        } catch(e: Exception){
            Log.d(TAG, "signIn: " + e.message)
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

    fun getCurrentUser(): FirebaseUser?{
        return mAuth.currentUser
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
    fun signOut(context: Context) {
        mAuth.signOut()

    }

}