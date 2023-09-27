package com.cutedomain.kittyreader.models

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.ChromeReaderMode
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Book
import androidx.compose.material.icons.outlined.ChromeReaderMode
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.cutedomain.kittyreader.screens.navigation.AppScreens


data class NavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unSelectedIcon: ImageVector,
    val badgeCount: Int?= null,
    val route: String
)

val items=listOf<NavigationItem>(
    NavigationItem(title="Home", selectedIcon = Icons.Default.Home, unSelectedIcon = Icons.Outlined.Home, route=AppScreens.LibraryScreen.route),
    NavigationItem(title="Books", selectedIcon = Icons.Default.Book, unSelectedIcon = Icons.Outlined.Book, route =AppScreens.LibraryScreen.route),
    NavigationItem(title="Login", selectedIcon = Icons.Default.Person, unSelectedIcon = Icons.Outlined.Person, route =AppScreens.LoginScreen.route),
    NavigationItem(title="Read", selectedIcon = Icons.Default.ChromeReaderMode, unSelectedIcon = Icons.Outlined.ChromeReaderMode, route =AppScreens.IntentScreen.route)
)
