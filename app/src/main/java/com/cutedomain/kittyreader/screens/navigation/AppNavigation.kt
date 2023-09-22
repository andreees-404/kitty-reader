package com.cutedomain.kittyreader.screens.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cutedomain.kittyreader.screens.AnimateScreen
import com.cutedomain.kittyreader.screens.ConfigScreen
import com.cutedomain.kittyreader.screens.IntentScreen
import com.cutedomain.kittyreader.screens.account.LoginScreen
import com.cutedomain.kittyreader.screens.account.RegisterScreen
import com.cutedomain.kittyreader.screens.library.LibraryScreen


// En este archivo navegaremos entre las pantallas
@Composable
fun AppNavigation(){
    val navController=rememberNavController()

    NavHost(navController =navController , startDestination = AppScreens.LibraryScreen.route){
        composable(route= AppScreens.LoginScreen.route){
            LoginScreen(navController)
        }
        composable(route= AppScreens.RegisterScreen.route){
            RegisterScreen(navController)
        }
        composable(route=AppScreens.LibraryScreen.route){
            LibraryScreen(navController)
        }
        composable(route=AppScreens.AnimateSplashScreen.route){
            AnimateScreen(navController)
        }
        composable(route=AppScreens.IntentScreen.route){
            IntentScreen(navController, LocalContext.current)
        }
        composable(route=AppScreens.ConfigScreen.route){
            ConfigScreen(navController)
        }
    }
}