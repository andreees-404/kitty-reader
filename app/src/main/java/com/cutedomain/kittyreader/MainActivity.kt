package com.cutedomain.kittyreader
/*
* @version: 0.0.5
* @author: Andrestio404
* @date: 27th August, 2023
* @notes: Para este proyecto se utiliza jetpack compose,
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
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity(){
    private companion object {
        private const val TAG: String = "MAIN_ACTIVITY"
        private const val STORAGE_PERMISSION_CODE = 100
    }


    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    private val grantedPerms = ArrayList<String>()
    private var deniedPerms = ArrayList<String>()

    private val storagePermissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        listOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.MANAGE_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET)
    } else {
        listOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET
        )
    }

    private val permissionsLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            if (Environment.isExternalStorageManager()){
                Log.d(TAG, "permissionsLauncher: Permissions granted")
                toast("External storage permissions granted!")
            }
        } else {
            Log.d(TAG, "permissionsLauncher: Permissions denied ")
            toast("External storage permissions denied!")
        }
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Iniciar el SDK de Facebook
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        val currentUser = mAuth.currentUser?.email

        // Full App
        installSplashScreen()
       setContent {
           KittyReaderTheme {
                if (checkPermissions(storagePermissions)){
                    if(currentUser != null){
                        AppNavigation(currentUser.toString())
                    }
                    else {

                        AppNavigation("Anónimo")
                    }
                }
               else {
                   toast("Request perms...")
                   requestPerms()
               }

           }
         }
       }


    @Preview
    @Composable
    private fun MainPreview(){
        AppNavigation("test@test.com")
    }

    /*
    * Verificar permisos de la app
    * @return Si los permisos han sido aceptados o rechazados
    */
    private fun checkPermissions(permissions: List<String>): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // This line return true or false
            Environment.isExternalStorageManager()
        } else {
            // Sobre Android 11(R)
            for (i in permissions) {
                if (ContextCompat.checkSelfPermission(this, i) != PackageManager.PERMISSION_GRANTED
                ) {
                    grantedPerms.add(i)
                } else {
                    // Existe al menos un permiso denegado
                    deniedPerms.add(i)
                }
                if (deniedPerms.isEmpty()){
                    return true
                }
            }

            return false
        }}



    /*
    * Solicita los permisos al usuario
    */
    private fun requestPerms(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            try {
                Log.d(TAG, "request permissions: try")
                val intent = Intent()
                intent.action = Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION
                val uri = Uri.fromParts("package", this.packageName, null)
                intent.data = uri
                permissionsLauncher.launch(intent)
            } catch (e: Exception){
                Log.d(TAG, "request permissions: ", e)
                val intent = Intent()
                intent.action = Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION
                permissionsLauncher.launch(intent)

            }
        } else {
            ActivityCompat.requestPermissions(this,
                storagePermissions.toTypedArray(), STORAGE_PERMISSION_CODE
            )
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == STORAGE_PERMISSION_CODE){
            if (grantResults.isNotEmpty()) {
                val write = grantResults[0] == PackageManager.PERMISSION_GRANTED
                val read = grantResults[1] == PackageManager.PERMISSION_GRANTED

                if (write && read){
                    Log.d(TAG, "requestPermissionResult: Permissions granted")

                } else {
                    Log.d(TAG, "requestPermissionResult: External storage permissions denied")
                    toast("External storage permissions denied!")
                }
            }
        }

    }
    /*
    * Desplegar un widget en la pantalla
    *
    * @param message
    *   Mensaje a escribir
    */
    private fun toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
    }






