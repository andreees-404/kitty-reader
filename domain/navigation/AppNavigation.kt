package com.domaintest.pdf_viewer_alpha.domain.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.domaintest.pdf_viewer_alpha.models.AppScreens
import com.domaintest.pdf_viewer_alpha.views.screens.*

@Composable
fun AppNavigation(){
    val navController=rememberNavController()
    
    NavHost(navController = navController, startDestination = AppScreens.IntentScreen.route){
        composable(route = AppScreens.LibraryScreen.route){
            LibraryScreen(navController)
        }
        composable(route = AppScreens.LoginScreen.route){
            LoginScreen(navController)
        }
        composable(route= AppScreens.SignUpScreen.route){
        }
        composable(route= AppScreens.IntentScreen.route){
            IntentScreen(navController, LocalContext.current)
        }

    }
}
