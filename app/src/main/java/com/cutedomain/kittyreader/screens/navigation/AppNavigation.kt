package com.cutedomain.kittyreader.screens.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cutedomain.kittyreader.screens.AnimateScreen
import com.cutedomain.kittyreader.screens.LibraryScreen
import com.cutedomain.kittyreader.screens.LoginScreen
import com.cutedomain.kittyreader.screens.RegisterScreen


// En este archivo navegaremos entre las pantallas
@Composable
fun AppNavigation(){
    val navController=rememberNavController()

    NavHost(navController =navController , startDestination = AppScreens.AnimateSplashScreen.route){
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
    }
}