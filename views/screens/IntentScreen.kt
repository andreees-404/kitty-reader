package com.domaintest.pdf_viewer_alpha.views.screens

import android.content.Context
import android.content.Intent
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.domaintest.pdf_viewer_alpha.views.screens.view.PdfActivity

@Composable
fun IntentScreen(navController: NavController, context: Context){
    Button(onClick = {
        Intent(context, PdfActivity::class.java).also {
            context.startActivity(it)
        }
    }) {
        Text(text = "Iniciar PDF Viewer")
    }
}