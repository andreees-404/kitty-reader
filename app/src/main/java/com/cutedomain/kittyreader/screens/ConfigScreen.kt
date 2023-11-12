package com.cutedomain.kittyreader.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth

val email = FirebaseAuth.getInstance().currentUser?.email

@Composable
fun ConfigScreen(navController: NavController){
    Column(
        modifier=Modifier.fillMaxSize()
    ) {
        if(email != null){
            Text(text = email)
        }
    }
}

@Preview
@Composable
fun configPreview(){
    ConfigScreen(navController = rememberNavController())
}