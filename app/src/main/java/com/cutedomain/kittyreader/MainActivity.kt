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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cutedomain.kittyreader.ui.theme.KittyReaderTheme

class MainActivity : ComponentActivity(){
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KittyReaderTheme {
                NavScreens()

            }
        }
            }

@Preview
@Composable
fun BarPreview(){
    val navController= rememberNavController()
    Register(navController)
}


    // Main Login
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun Login(navController: NavController) {
        Scaffold(
            topBar = {
                TopAppBar(title = {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        style = TextStyle(
                            color = Color(0xFFFFFFFF),
                            fontSize = 24.sp
                        ),

                        )
                }, colors = TopAppBarDefaults.smallTopAppBarColors(containerColor=Color(
                    0xFFFF72A2
                )
                )
                )
            }
        ){
            Box(
                Modifier
                    .fillMaxSize()
                    .background(Color(0xFFFFFFFF))) {
                // Variable email
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
                                color = Color(0xFFFF95BA),
                                fontWeight = FontWeight.Bold
                            ),
                        )
                    }
                    // Useranme
                    TextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Email", style = TextStyle.Default.copy(fontSize = 14.sp)) },
                        leadingIcon = {
                            Icon(imageVector=Icons.Filled.Email, contentDescription = "Email input")
                        }

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
                        },
                        leadingIcon={
                            Icon(imageVector = Icons.Filled.Lock, contentDescription = "Password input")
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
                        colors = ButtonDefaults.elevatedButtonColors(containerColor=Color(0xFFFF5E94))
                    ) {
                        Text(text = "Iniciar Sesión",style=TextStyle(color=Color(0xFFFFFFFF)))
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
                        onClick = {
                            navController.navigate("Register")
                        },

                        /* { offset ->
                                linkRegistrar.getStringAnnotations(
                                    tag = "register",
                                    start = offset,
                                    end = offset
                                ).firstOrNull()?.let {
                                    Log.d("register URL", it.item)
                                }*/
                        //}
                    )
                }

            }
        }

        }

    @Composable
    fun NavScreens(){
        val navController=rememberNavController()
        NavHost(navController=navController, startDestination="Login"){
            composable("Login"){
                Login(navController)
            }
            composable("Register"){
                Register(navController)
        }
        }
    }

    @Preview
    @Composable
    fun AppPreview() {
        NavScreens()
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun Register(navController: NavController) {

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = stringResource(id = R.string.app_name),
                            style = TextStyle(
                                color = Color(0xFFFFFFFF),
                                fontSize = 24.sp
                            )
                        )
                    },
                    colors = TopAppBarDefaults.smallTopAppBarColors(Color(0xFFFF72A2)),
                    navigationIcon = {
                        IconButton(onClick = { navController.navigate("Login") }) {
                            Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back to login", tint = Color(0xFFFFFFFF))
                        }
                    }
                )
            }
        ) {
            Box(
                Modifier
                    .fillMaxSize()
                    .background(Color(0xFFFFFFFF)),
            ) {

                // Name
                var firstName by rememberSaveable {
                    mutableStateOf("")
                }
                // Last Name
                var lastName by rememberSaveable {
                    mutableStateOf("")
                }
                // Email
                var email by rememberSaveable {
                    mutableStateOf("")
                }

                // Password
                var password by rememberSaveable {
                    mutableStateOf("")
                }

                // Main column
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(
                            PaddingValues(
                                top = 80.dp,
                                start = 40.dp,
                                end = 40.dp,
                                bottom = 40.dp
                            )
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Column {
                        Text(
                            text = "Regístrate",
                            style = TextStyle(fontSize = 26.sp),
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFFF95BA)
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        // Name input
                        TextField(
                            value = firstName, onValueChange = {
                                firstName = it
                            },
                            label = {
                                Text(
                                    text = "First name",
                                    style = TextStyle.Default.copy(fontSize = 14.sp)
                                )
                            },
                            modifier = Modifier.padding(PaddingValues(bottom = 20.dp))
                        )

                        // Last name input
                        TextField(
                            value = lastName, onValueChange = {
                                lastName = it
                            },
                            label = {
                                Text(
                                    text = "Last name",
                                    style = TextStyle.Default.copy(fontSize = 14.sp)
                                )
                            },
                            modifier = Modifier.padding(PaddingValues(bottom = 20.dp))
                        )


                        // Email input
                        TextField(value = email, onValueChange = {
                            email = it
                        },
                            label = {
                                Text(
                                    text = "Email",
                                    style = TextStyle.Default.copy(fontSize = 14.sp)
                                )
                            },
                            modifier = Modifier.padding(PaddingValues(bottom = 50.dp)),
                            leadingIcon = {
                                val userIcon = Icons.Filled.Mail
                                val desc = "user icon"

                                IconButton(onClick = {}) {
                                    Icon(imageVector = userIcon, contentDescription = desc)
                                }
                            }
                        )

                        // toggle password
                        var visiblePass by rememberSaveable {
                            mutableStateOf(false)
                        }


                        Text(
                            "Crea una contraseña",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            style = TextStyle(color=Color(0xFFFF95BA))
                        )
                        // Password input
                        TextField(value = password, onValueChange = {
                            password = it
                        },
                            label = {
                                Text(
                                    text = "Password",
                                    style = TextStyle.Default.copy(fontSize = 14.sp)
                                )
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            singleLine = true,
                            modifier = Modifier.padding(PaddingValues(top = 20.dp, bottom = 10.dp)),
                            visualTransformation = if (visiblePass) VisualTransformation.None else PasswordVisualTransformation(),
                            trailingIcon = {
                                val icon = if (visiblePass) {
                                    Icons.Filled.Visibility
                                } else {
                                    Icons.Filled.VisibilityOff
                                }
                                // visible
                                val description = if (visiblePass) "Hide password" else "Show password"
                                IconButton(onClick = { visiblePass = !visiblePass }) {
                                    Icon(imageVector = icon, description)
                                }
                            },
                            leadingIcon = {
                                IconButton(onClick = { /*TODO*/ }) {
                                    Icon(
                                        imageVector = Icons.Filled.Lock,
                                        contentDescription = "Password icon"
                                    )
                                }
                            }
                        )


                        var confirmPassword by remember {
                            mutableStateOf("")
                        }
                        // Confirm password
                        TextField(value = confirmPassword, onValueChange = {
                            confirmPassword = it

                        },
                            label = {
                                Text(
                                    text = "Confirm Password",
                                    style = TextStyle.Default.copy(fontSize = 14.sp)
                                )
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            singleLine = true,
                            modifier = Modifier.padding(PaddingValues(top = 20.dp)),
                            visualTransformation = if (visiblePass) VisualTransformation.None else PasswordVisualTransformation(),
                            trailingIcon = {
                                val confirmIcon = if (visiblePass) {
                                    Icons.Filled.Visibility
                                } else {
                                    Icons.Filled.VisibilityOff
                                }
                                // visible
                                val confirmDescription =
                                    if (visiblePass) "Hide password" else "Show password"
                                IconButton(onClick = { visiblePass = !visiblePass }) {
                                    Icon(imageVector = confirmIcon, confirmDescription)
                                }
                            }
                        )

                        // Privacy policy and condition terms
                        // ----------------------------------------------------------
                        var selected by rememberSaveable {
                            mutableStateOf(false)
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(PaddingValues(top = 30.dp, bottom = 20.dp)),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row {
                                Checkbox(checked = selected, onCheckedChange = { selected = it })
                                Spacer(modifier = Modifier.width(5.dp))
                                Text(
                                    text = "Acepto todos los términos y condiciones de privacidad.",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp,
                                    color = Color.Black,
                                    modifier = Modifier.align(Alignment.CenterVertically)
                                )
                            }
                        }
                        // ----------------------------------------------------------
                        Button(
                            onClick = { /*TODO*/ },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.elevatedButtonColors(Color(0xFFFF5E94))
                        ) {
                            Text(text = "Enviar", style = TextStyle(color = Color(0xFFFFFFFF)))
                        }

                    }
                }

            }
        }
/*
        // Name
        var firstName by rememberSaveable {
            mutableStateOf("")
        }
        // Last Name
        var lastName by rememberSaveable {
            mutableStateOf("")
        }
        var phone by rememberSaveable {
            mutableStateOf("")
        }
        // Email
        var email by rememberSaveable {
            mutableStateOf("")
        }

        // Password
        var password by rememberSaveable {
            mutableStateOf("")
        }

        // Main column
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(PaddingValues(top = 80.dp, start = 40.dp, end = 40.dp, bottom = 40.dp)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Column {
                Text(
                    text = "Regístrate",
                    style = TextStyle(fontSize = 26.sp),
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFFF95BA)
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Name input
                TextField(
                    value = firstName, onValueChange = {
                        firstName = it
                    },
                    label = {
                        Text(
                            text = "First name",
                            style = TextStyle.Default.copy(fontSize = 14.sp)
                        )
                    },
                    modifier = Modifier.padding(PaddingValues(bottom = 20.dp))
                )

                // Last name input
                TextField(
                    value = lastName, onValueChange = {
                        lastName = it
                    },
                    label = {
                        Text(
                            text = "Last name",
                            style = TextStyle.Default.copy(fontSize = 14.sp)
                        )
                    },
                    modifier = Modifier.padding(PaddingValues(bottom = 20.dp))
                )


                // Email input
                TextField(value = email, onValueChange = {
                    email = it
                },
                    label = {
                        Text(
                            text = "Email",
                            style = TextStyle.Default.copy(fontSize = 14.sp)
                        )
                    },
                    modifier = Modifier.padding(PaddingValues(bottom = 50.dp)),
                    leadingIcon = {
                        val userIcon = Icons.Filled.Mail
                        val desc = "user icon"

                        IconButton(onClick = {}) {
                            Icon(imageVector = userIcon, contentDescription = desc)
                        }
                    }
                )

                // toggle password
                var visiblePass by rememberSaveable {
                    mutableStateOf(false)
                }


                Text(
                    "Crea una contraseña",
                    fontSize = 16.sp
                )
                // Password input
                TextField(value = password, onValueChange = {
                    password = it
                },
                    label = {
                        Text(
                            text = "Password",
                            style = TextStyle.Default.copy(fontSize = 14.sp)
                        )
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    singleLine = true,
                    modifier = Modifier.padding(PaddingValues(top = 20.dp, bottom = 10.dp)),
                    visualTransformation = if (visiblePass) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        val icon = if (visiblePass) {
                            Icons.Filled.Visibility
                        } else {
                            Icons.Filled.VisibilityOff
                        }
                        // visible
                        val description = if (visiblePass) "Hide password" else "Show password"
                        IconButton(onClick = { visiblePass = !visiblePass }) {
                            Icon(imageVector = icon, description)
                        }
                    },
                    leadingIcon = {
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(
                                imageVector = Icons.Filled.Lock,
                                contentDescription = "Password icon"
                            )
                        }
                    }
                )


                var confirmPassword by remember {
                    mutableStateOf("")
                }
                // Confirm password
                TextField(value = confirmPassword, onValueChange = {
                    confirmPassword = it

                },
                    label = {
                        Text(
                            text = "Confirm Password",
                            style = TextStyle.Default.copy(fontSize = 14.sp)
                        )
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    singleLine = true,
                    modifier = Modifier.padding(PaddingValues(top = 20.dp)),
                    visualTransformation = if (visiblePass) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        val confirmIcon = if (visiblePass) {
                            Icons.Filled.Visibility
                        } else {
                            Icons.Filled.VisibilityOff
                        }
                        // visible
                        val confirmDescription =
                            if (visiblePass) "Hide password" else "Show password"
                        IconButton(onClick = { visiblePass = !visiblePass }) {
                            Icon(imageVector = confirmIcon, confirmDescription)
                        }
                    }
                )

                // Privacy policy and condition terms
                // ----------------------------------------------------------
                var selected by rememberSaveable {
                    mutableStateOf(false)
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(PaddingValues(top = 30.dp, bottom = 20.dp)),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row {
                        Checkbox(checked = selected, onCheckedChange = { selected = it })
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = "Acepto todos los términos y condiciones.",
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            color = Color.Black,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                    }
                }
                // ----------------------------------------------------------
                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.elevatedButtonColors(Color(0xFFFF5E94))
                ) {
                    Text(text = "Enviar", style = TextStyle(color = Color(0xFFFFFFFF)))
                }

            }
      }*/
    }


    }


