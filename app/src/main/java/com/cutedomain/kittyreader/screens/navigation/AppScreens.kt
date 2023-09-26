package com.cutedomain.kittyreader.screens.navigation
// Aquí definiremos las rutas de nuestra app
sealed class AppScreens(val route: String){
    // Definimos los objetos con sus respectivas rutas
    //  -> login_screen hace referencia al fichero LoginScreen
    object LoginScreen: AppScreens(route = "login_screen")
    object RegisterScreen: AppScreens(route = "register_screen")
    object LibraryScreen: AppScreens(route = "library_screen")
    object AnimateSplashScreen: AppScreens(route = "loading_screen")
    object BookShelfScreen: AppScreens(route = "books_screen")
    object ConfigScreen: AppScreens(route = "config_screen")
    object IntentScreen: AppScreens(route = "intent_screen")
    object BookScreen: AppScreens(route= "reader_screen")

}
