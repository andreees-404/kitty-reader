package com.cutedomain.kittyreader
/*
* Version: 0.0.1
* Author: Andrestio404
* Date: 27th August, 2023
* Notes: Para este proyecto se utiliza jetpack compose,
*        por lo que se recomienda investigar su documentación.
*
* Changes: (1) Se ha creado la interfaz visual necesaria para codificar
*           el funcionamiento de cada componente
* Changes (2): Se ha creado el panel de login aún sin funcionamiento
* Changes (3): Pantalla de registro creada con botón para volver al login
* Changes (4): Se ha establecido la comunicación de la página de Login con la página de Registro
* */
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.cutedomain.kittyreader.screens.navigation.AppNavigation
import com.cutedomain.kittyreader.ui.theme.KittyReaderTheme



class MainActivity : ComponentActivity(){

   // private lateinit var binding: ActivityMainBinding
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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


