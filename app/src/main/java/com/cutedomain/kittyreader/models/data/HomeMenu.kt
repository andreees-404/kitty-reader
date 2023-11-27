package com.cutedomain.kittyreader.models.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Person2
import androidx.compose.material.icons.outlined.Book
import androidx.compose.material.icons.outlined.CodeOff
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Person2
import androidx.compose.ui.graphics.vector.ImageVector
import com.cutedomain.kittyreader.screens.navigation.AppScreens

/*
* Author: Andresito 404
* Date: 01-10-2023
*
* En esta clase se definen
* los ítems que contiene
* la barra lateral de la librería principal
* */
data class NavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unSelectedIcon: ImageVector,
    val badgeCount: Int?= null,
    val route: String
)


// This is a items of lateral menu
val items=listOf<NavigationItem>(
    // Pantalla principal
    NavigationItem(title="Home", selectedIcon = Icons.Default.Home, unSelectedIcon = Icons.Outlined.Home, route=AppScreens.LibraryScreen.route),

    // Pantalla de estantería
    NavigationItem(title="Books", selectedIcon = Icons.Default.Book, unSelectedIcon = Icons.Outlined.Book, route=AppScreens.LibraryScreen.route),

    // Inicio y registro de usuarios
    NavigationItem(title="Login", selectedIcon = Icons.Default.Person, unSelectedIcon = Icons.Outlined.Person, route=AppScreens.LoginScreen.route),

    NavigationItem(title = "Mqtt" , selectedIcon = Icons.Default.Code, unSelectedIcon = Icons.Outlined.CodeOff, route = AppScreens.MqttScreen.route)
    )

val itemsLogged = listOf<NavigationItem>(

    NavigationItem(title="Home", selectedIcon = Icons.Default.Home, unSelectedIcon = Icons.Outlined.Home, route=AppScreens.LibraryScreen.route),

    // Pantalla de estantería
    NavigationItem(title="Books", selectedIcon = Icons.Default.Book, unSelectedIcon = Icons.Outlined.Book, route=AppScreens.LibraryScreen.route),

    NavigationItem(title="Profile", selectedIcon = Icons.Default.Person2, unSelectedIcon = Icons.Outlined.Person2, route=AppScreens.ConfigScreen.route),

    NavigationItem(title = "Mqtt" , selectedIcon = Icons.Default.Code, unSelectedIcon = Icons.Outlined.CodeOff, route = AppScreens.MqttScreen.route)

)
