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
* */
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import com.cutedomain.kittyreader.R.drawable.icon_visible
import com.cutedomain.kittyreader.ui.theme.KittyReaderTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KittyReaderTheme {
                LoginMenu()

            }
        }
            }

@Preview
@Composable
fun BarPreview(){
    LoginMenu()
}
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "InvalidColorHexValue")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun LoginMenu(){
        Scaffold(
            topBar = {
                    TopAppBar(title = {
                        Text(text = "Login",
                            style = TextStyle(color = MaterialTheme.colorScheme.background,
                                            fontSize=24.sp),

                        )
                    }, colors = TopAppBarDefaults.smallTopAppBarColors(containerColor=MaterialTheme.colorScheme.tertiary
                    )
                    )

            }
        ){
            Box(
                Modifier
                    .fillMaxSize()
                    .background(Color(0xFFFFF2CC))) {

                Login()
            }
        }

    }

    // Main Login
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun Login() {
                // Variable username
                var name by rememberSaveable {
                    mutableStateOf("")
                }
                // Variable password
                var pass by rememberSaveable {
                    mutableStateOf("")
                }
                // Todo el contenido en una columnas
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(20.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                )
                {
                    Column(
                        horizontalAlignment = Alignment.Start,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 40.dp, bottom = 20.dp)
                    ) {
                        Text(
                            text = "Ingresa tu cuenta",
                            style = TextStyle(
                                fontSize = 24.sp,
                                color = MaterialTheme.colorScheme.onBackground
                            ),
                        )
                    }
                    // Useranme
                    TextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Email", style = TextStyle.Default.copy(fontSize = 14.sp)) },

                    )

                    Spacer(
                        modifier = Modifier.height(30.dp)
                    )

                    var visiblePass by remember {
                        mutableStateOf(false)
                    }
                    // Password
                    TextField(
                        value = pass,
                        onValueChange = { pass = it },
                        label = {
                            Text(
                                "Password",
                                style = TextStyle.Default.copy(fontSize = 14.sp)
                            )
                        },
                        visualTransformation = if (visiblePass) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        singleLine = true,
                        trailingIcon = {
                            val image = if (visiblePass) {
                                Icons.Filled.Visibility
                            } else {
                                Icons.Filled.VisibilityOff
                            }

                            val description = if (visiblePass) "Hide Password" else "Show password"

                            IconButton(onClick = { visiblePass = !visiblePass }) {
                                Icon(imageVector = image, description)
                            }
                        }

                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = { },
                        contentPadding = PaddingValues(
                            start = 20.dp,
                            top = 12.dp,
                            end = 20.dp,
                            bottom = 12.dp
                        ),
                        colors = ButtonDefaults.elevatedButtonColors(containerColor=MaterialTheme.colorScheme.tertiary)
                    ) {
                        Text(text = "Iniciar Sesión",style=TextStyle(color=MaterialTheme.colorScheme.tertiaryContainer))
                    }
                    Spacer(modifier = Modifier.height(15.dp))
                    val linkRegistrar = buildAnnotatedString {
                        withStyle(style=SpanStyle(color=MaterialTheme.colorScheme.onBackground)){
                            append("¿No tienes una cuenta?, ")
                        }
                        pushStringAnnotation(
                            "register",
                            annotation = "https://google.com"
                        )
                        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.tertiary)) {
                            append("Regístrate")
                        }
                        // What does the function
                        pop()
                    }
                    // Texto tipo link
                    ClickableText(
                        text = linkRegistrar,
                        onClick = { offset ->
                            linkRegistrar.getStringAnnotations(
                                tag = "register",
                                start = offset,
                                end = offset
                            ).firstOrNull()?.let {
                                Log.d("register URL", it.item)
                            }
                        }
                    )
                }
        }
    }


