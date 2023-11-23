package com.cutedomain.kittyreader.views

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.cutedomain.kittyreader.screens.navigation.AppNavigation
import com.cutedomain.kittyreader.ui.theme.KittyReaderTheme

class MainActivity : ComponentActivity() {
    private companion object {
        private const val TAG: String = "MAIN_ACTIVITY"
    }


    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        // Full App
        installSplashScreen()
        setContent {
            KittyReaderTheme {
                AppNavigation()
            }
        }
    }


}










