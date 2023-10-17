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
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.cutedomain.kittyreader.screens.navigation.AppNavigation
import com.cutedomain.kittyreader.ui.theme.KittyReaderTheme
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger

class MainActivity : ComponentActivity(){
    private companion object {
        private const val TAG: String = "MAIN_ACTIVITY"
    }



    // Request permissions
    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            if (Environment.isExternalStorageManager()){
                Log.d(TAG, "permissionsLauncher: Permissions granted")
                Toast.makeText(this, "Permissions granted!", Toast.LENGTH_SHORT).show()
            }
        }
    }


    //private val storagePermission=Manifest.permission.READ_EXTERNAL_STORAGE
    val permissions= if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.MANAGE_EXTERNAL_STORAGE,
            Manifest.permission.READ_MEDIA_IMAGES,
            Manifest.permission.READ_MEDIA_VIDEO,
            Manifest.permission.INTERNET
        )
    } else {
        arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.MANAGE_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET,
        )
    }


    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Iniciar el SDK de Facebook
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        // Full App
        installSplashScreen()
       setContent {
           KittyReaderTheme {
               if (checkPermisison()){
                   Log.d(TAG, "onCreate: Perms granted, starting...")
                   AppNavigation()
               } else {
                   Log.d(TAG, "onCreate: Permission were not granted!")
                   requestPerms()
               }
           }
       }
       }

    private fun checkPermisison(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            Environment.isExternalStorageManager()
        } else {
            for (i in permissions){
                if (ContextCompat.checkSelfPermission(this, i) != PackageManager.PERMISSION_GRANTED){
                    grantedPerms.add(i)
                } else {
                    deniedPerms.add(i)
                }
                if (deniedPerms.isEmpty()) return true
            }
            // Si existe un permiso rechazado
            return false;
        }
    }

    private fun requestPerms(){
        if (SDK_INT > Build.VERSION_CODES.R){
            try {
                Log.d(TAG, "requestPerms: try request perms")
                val intent = Intent()
                intent.action = Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION
                val uri = Uri.fromParts("package", this.packageName, null)
                intent.data = uri
                requestPermissionLauncher.launch(intent)
            } catch (e: Exception) {
                Log.d(TAG, "requestPerms: ${e.stackTrace}")
                val intent = Intent()
                intent.action = Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION
                requestPermissionLauncher.launch(intent)
            }
        } else {
            ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    @Preview
    @Composable
    private fun MainPreview(){
        AppNavigation()
    }
    }






