package com.cutedomain.kittyreader.domain.utils

import android.app.AlertDialog
import android.content.Context
import androidx.compose.runtime.Composable


/*
* Author: Andresito404
* Date: 01-10-2023
*
* Este archivo contiene las vistas de los diálogos
* y alertas que se muestran en la app
* @param context -> Hacer referencia a la pantalla actual
* @param message -> Texto que contendrá el diálogo
* */

@Composable
fun MessageDialog(context: Context, message: String){
    // Create AlertDialog Here
    val builder= AlertDialog.Builder(context)
    builder.setTitle("Error")
    builder.setMessage(message)
    builder.setPositiveButton("Aceptar", null)
    val dialog: AlertDialog = builder.create()
    dialog.show()
}



