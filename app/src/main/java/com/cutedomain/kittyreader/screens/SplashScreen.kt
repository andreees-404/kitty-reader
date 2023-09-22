package com.cutedomain.kittyreader.screens

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LibraryBooks
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.cutedomain.kittyreader.R
import com.cutedomain.kittyreader.screens.navigation.AppScreens
import kotlinx.coroutines.delay
// notes: updated splashScreen with fade animation

@Composable
fun AnimateScreen(navController: NavController){
    var startAnimation: Boolean by remember { mutableStateOf(false) }
    val alphaAnim= animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(
            durationMillis = 3000
        ), label = ""
    )
    LaunchedEffect(key1 = true ){
        startAnimation=true
        delay(3000)
        navController.navigate(AppScreens.LibraryScreen.route)
    }
    SplashScreen(alphaAnimation = alphaAnim.value)
}
@Composable
fun SplashScreen(alphaAnimation: Float){
    Box(
        modifier= Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.main_color)),
            contentAlignment = Alignment.Center
        ) {
       Icon(modifier= Modifier
           .size(120.dp)
           .alpha(alphaAnimation),
           imageVector = Icons.Default.LibraryBooks ,
           contentDescription = null, tint = colorResource(id = R.color.white)
       )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview
@Composable
fun PreviewScreen(){
    SplashScreen(alphaAnimation = 1f)
}

