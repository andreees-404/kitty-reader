package com.cutedomain.kittyreader
/*
* Version: 0.0.1
* Author: Andrestio404
* Date: 27th August, 2023
* Notes: Para este proyecto se utiliza jetpack compose,
*        por lo que se recomienda investigar su documentación.
* Changes: (1) Se ha creado la interfaz visual necesaria para codificar
*           el funcionamiento de cada componente
* Changes (2): Se ha creado el panel de login aún sin funcionamiento
* Changes (3): Pantalla de registro creada con botón para volver al login
* Changes (4): Se ha establecido la comunicación de la página de Login con la página de Registro
* Changes (5): Se ha agregado autenticación basada en Firebase -> email y contraseña
* */
import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.cutedomain.kittyreader.screens.navigation.AppNavigation
import com.cutedomain.kittyreader.ui.theme.KittyReaderTheme
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger

class MainActivity : ComponentActivity(){

    // Request permissions
    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){
        isGranted -> val message = if (isGranted) "Permission Granteed" else "Permission Denied"
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
    //private val storagePermission=Manifest.permission.READ_EXTERNAL_STORAGE
    val permissions= if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        arrayOf(
            Manifest.permission.READ_MEDIA_AUDIO,
            Manifest.permission.READ_MEDIA_IMAGES,
            Manifest.permission.READ_MEDIA_VIDEO,
            Manifest.permission.INTERNET
        )
    } else {
        arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET,
        )
    }
    val REQUEST_CODE=100


    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Iniciar el SDK de Facebook
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        ActivityCompat.requestPermissions(
            this, permissions, PackageManager.PERMISSION_GRANTED)

        // Full App
        installSplashScreen()
       setContent {
           KittyReaderTheme {
               AppNavigation()
           }
       }
       }


    @Preview
    @Composable
    private fun MainPreview(){
        AppNavigation()
    }
    }






