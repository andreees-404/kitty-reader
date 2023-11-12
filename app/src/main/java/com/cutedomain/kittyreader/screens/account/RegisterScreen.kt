package com.cutedomain.kittyreader.screens.account

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
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
import androidx.navigation.compose.rememberNavController
import com.cutedomain.kittyreader.R
import com.cutedomain.kittyreader.domain.controllers.UserController
import com.cutedomain.kittyreader.screens.navigation.AppScreens


// Este archivo contiene la
// vista de registro de usuarios


// private val userViewModel = viewModel { UserViewModel() }
private val userController: UserController= UserController()
private var passwordMessage: String = ""
private var isValidEmail: String = ""

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(navController: NavController) {
    Scaffold( topBar = {
        TopAppBar( title = {
            Text( text = stringResource(id = R.string.app_name), style = TextStyle(color = Color(0xFFFFFFFF), fontSize = 24.sp)) },
            colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color(0xFFFF72A2)),
            navigationIcon = {
                IconButton(onClick = { navController.navigate(AppScreens.LoginScreen.route) }) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back to login", tint = Color(0xFFFFFFFF))
                        }
                    }
                )
            }
    ) {
        // Content
        RegisterForm(navController)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterForm(navController: NavController){
    val linkInicioSesion = buildAnnotatedString {
        withStyle(style= SpanStyle(color=Color(0xFF111111) )){
            append("¿Ya tienes una cuenta?, ")
        }
        pushStringAnnotation(
            "register",
            annotation = "https://google.com"
        )
        withStyle(style = SpanStyle(color=Color(0xFFFF72A2))) {
            append("Inicia sesión")
        }
        // What does the function
        pop()
    }
    Column(
        modifier= Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFFFF)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Name
        var username by rememberSaveable {
            mutableStateOf("")
        }

        // Email
        var email by rememberSaveable {
            mutableStateOf("")
        }

        // 1st Password
        var password by rememberSaveable {
            mutableStateOf("")
        }

        // 2nd Password value
        var confirmPassword by remember {
            mutableStateOf("")
        }

        // toggle password
        var visiblePass by rememberSaveable {
            mutableStateOf(false)
        }

        // CheckBox is selected
        var selected by rememberSaveable {
            mutableStateOf(false)
        }

        val context=LocalContext.current


        // Main column
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .verticalScroll(rememberScrollState())
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
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Regístrate", style = TextStyle(fontSize = 26.sp), fontWeight = FontWeight.Bold, color = Color(0xFFFF95BA))
                Spacer(modifier = Modifier.height(20.dp))

                // Create a user profile
                // ----------------

                // Username input
                TextField(
                    value = username, onValueChange = { username = it },
                    label = { Text( text = "Username", style = TextStyle.Default.copy(fontSize = 14.sp)) },
                    modifier = Modifier.padding(PaddingValues(bottom = 20.dp)),
                    singleLine = true)



                // Email input
                // -----------
                TextField(value = email, onValueChange = {
                    email = it
                    if (userController.verifyEmail(email)){
                        isValidEmail=""
                    } else { isValidEmail="* El email introducido no cumple con los requisitos."}},
                    label = { Text( text = "Email", style = TextStyle.Default.copy(fontSize = 14.sp)) },
                    singleLine = true,)

                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .padding(PaddingValues(top = 10.dp))
                        .fillMaxWidth()
                ) {
                    Text(text = isValidEmail, style = TextStyle(color = colorResource(id = R.color.red)), modifier = Modifier.padding(PaddingValues(bottom = 30.dp, start = 20.dp)))
                }

                Text("Crea una contraseña", fontSize = 20.sp, fontWeight = FontWeight.Bold, style = TextStyle(color= Color(0xFFFF95BA)))


                // Create password
                // ---------------
                TextField(value = password, onValueChange = { password = it
                    userController.checkPass(password, confirmPassword)},
                    label = { Text( text = "Password", style = TextStyle.Default.copy(fontSize = 14.sp))
                    }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    singleLine = true,
                    modifier = Modifier.padding(PaddingValues(top = 20.dp, bottom = 10.dp)),
                    visualTransformation = if (visiblePass) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        val icon = if (visiblePass) {
                            Icons.Filled.Visibility
                        } else {
                            Icons.Filled.VisibilityOff
                        }

                        // visible password
                        val description = if (visiblePass) "Hide password" else "Show password"
                        IconButton(onClick = { visiblePass = !visiblePass }) {
                            Icon(imageVector = icon, description) }
                    })

                // Confirm password
                // ----------------
                TextField(value = confirmPassword, onValueChange = {
                    confirmPassword = it
                    if (!userController.checkPass(password, confirmPassword)){
                        passwordMessage = "* Las contraseñas no coinciden"
                    } else { passwordMessage = "" }},
                    label = { Text( text = "Confirm Password", style = TextStyle.Default.copy(fontSize = 14.sp)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    singleLine = true,
                    modifier = Modifier.padding(PaddingValues(top = 20.dp)),
                    visualTransformation = if (visiblePass) VisualTransformation.None else PasswordVisualTransformation())

                Text(modifier = Modifier
                    .padding(PaddingValues(top = 10.dp, start = 20.dp))
                    .fillMaxWidth(),text = passwordMessage, style = TextStyle(color= colorResource(id = R.color.red)))
                // Validar que coinciden las contraseñas


                // Privacy policy and condition terms
                // ----------------------------------
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
                            text = stringResource(id = R.string.termString),
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            color = Color.Black,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                    }
                }
                // ----------------------------------------------------------
                Button(
                    onClick = {
                        if(selected){
                            userController.signUp(email.trim(), password.trim(), context)
                    } else {
                        Toast.makeText(context, "Por favor acepta los términos y condiciones", Toast.LENGTH_SHORT).show()
                    }
                              },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.elevatedButtonColors(Color(0xFFFF5E94))
                ) {
                    Text(text = stringResource(id = R.string.send), style = TextStyle(color = Color(0xFFFFFFFF)))
                }
                ClickableText(
                    text = linkInicioSesion,
                    onClick = {
                        navController.navigate(AppScreens.LoginScreen.route)
                    },
                    modifier= Modifier.padding(PaddingValues(top=20.dp))
                )
            }
        }

    }

}

@Preview
@Composable
fun PreviewRegister(){
    RegisterScreen(navController = rememberNavController())
}