package com.cutedomain.kittyreader.screens

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun IntentScreen(navController: NavController, context: Context){
    Column {
        Button(onClick = {}) {
            Text(text = "Iniciar activity")
        }
    }
}