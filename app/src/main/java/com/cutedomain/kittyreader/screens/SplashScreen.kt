package com.cutedomain.kittyreader.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.cutedomain.kittyreader.R
import com.cutedomain.kittyreader.screens.navigation.AppScreens

@Composable
fun SplashScreen(navController: NavController){
    Column(
        modifier= Modifier
            .fillMaxSize()
            .background(Color(0xFFFFEBF2)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
       Image(painter = painterResource(id = R.drawable.ic_launcher_foreground), contentDescription = "Loading screen")
        CircularProgressIndicator()
        Button(onClick = { navController.navigate(AppScreens.LibraryScreen.route) }) {

        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview
@Composable
fun PreviewScreen(){
    Scaffold (
        content = {
                SplashScreen(navController = rememberNavController())
        }
    )


}

