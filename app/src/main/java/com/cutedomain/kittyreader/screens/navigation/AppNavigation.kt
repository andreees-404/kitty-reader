package com.cutedomain.kittyreader.screens.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cutedomain.kittyreader.screens.account.LoginScreen
import com.cutedomain.kittyreader.screens.account.RegisterScreen
import com.cutedomain.kittyreader.screens.library.ChooseFile
import com.cutedomain.kittyreader.screens.library.LibraryScreen
import com.cutedomain.kittyreader.screens.others.AnimateScreen
import com.cutedomain.kittyreader.screens.others.ConfigScreen
import com.cutedomain.kittyreader.screens.others.GPScreen
import com.cutedomain.kittyreader.screens.others.MqttScreen


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
        composable(route=AppScreens.ConfigScreen.route){
            ConfigScreen(navController)
        }
        composable(route=AppScreens.ChooseFile.route){
            ChooseFile(navController)
        }
        composable(route=AppScreens.GPScreen.route){
            GPScreen(navController)
        }
        composable(route = AppScreens.MqttScreen.route){
            MqttScreen()
        }
    }
}
