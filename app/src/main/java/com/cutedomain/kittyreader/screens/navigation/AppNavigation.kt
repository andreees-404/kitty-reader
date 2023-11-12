package com.cutedomain.kittyreader.screens.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cutedomain.kittyreader.screens.AnimateScreen
import com.cutedomain.kittyreader.screens.ChooseFile
import com.cutedomain.kittyreader.screens.ConfigScreen
import com.cutedomain.kittyreader.screens.account.LoginScreen
import com.cutedomain.kittyreader.screens.account.RegisterScreen
import com.cutedomain.kittyreader.screens.library.LibraryScreen


// En este archivo navegaremos entre las pantallas
@Composable
fun AppNavigation(currentUser: String){
    val navController=rememberNavController()

    NavHost(navController =navController , startDestination = AppScreens.LibraryScreen.route){
        composable(route= AppScreens.LoginScreen.route){
            LoginScreen(navController)
        }
        composable(route= AppScreens.RegisterScreen.route){
            RegisterScreen(navController)
        }
        composable(route=AppScreens.LibraryScreen.route){
            if (currentUser == null){
                LibraryScreen(navController, "")
            }
            LibraryScreen(navController, currentUser)
        }
        composable(route=AppScreens.AnimateSplashScreen.route){
            AnimateScreen(navController)
        }
        composable(route=AppScreens.ConfigScreen.route){
            ConfigScreen(navController)
        }
        composable(route=AppScreens.ChooseFile.route){
            ChooseFile(navController)
        }
    }
}