package com.domaintest.pdf_viewer_alpha.models

sealed class AppScreens(val route: String){
    // Aquí definimos las pantallas de nuestra app
    object LoginScreen: AppScreens(route = "login")
    object LibraryScreen: AppScreens(route = "library")
    object SignUpScreen: AppScreens(route = "signUp")
    object IntentScreen: AppScreens(route = "intent")
}

