package com.cutedomain.kittyreader.screens.others

import android.content.Intent
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.cutedomain.kittyreader.views.MapsActivity

@Composable
fun GPScreen(navController: NavController){
    val context = LocalContext.current
    Button(onClick = {

        Intent(context, MapsActivity::class.java).also{
            context.startActivity(it)
        }

    }){
        Text("Mostrar tu ubicación")
    }
}