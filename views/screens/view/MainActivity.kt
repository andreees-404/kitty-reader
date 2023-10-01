package com.domaintest.pdf_viewer_alpha.views.screens.view

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.domaintest.pdf_viewer_alpha.domain.navigation.AppNavigation

class MainActivity : ComponentActivity() {
    // Permissions
    val permissions = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Pedir los permisos
        requestPermissions(permissions, 100)
        setContent {
            AppNavigation()
        }
    }


}