package com.cutedomain.kittyreader.screens

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.cutedomain.kittyreader.JavaActivity

@Composable
fun IntentScreen(navController: NavController, context: Context){
    Column {
        Button(onClick = {
            val intent=Intent(context, JavaActivity::class.java).also {
                context.startActivity(it)
            }
        }) {
            Text(text = "Iniciar activity")
        }
    }
}