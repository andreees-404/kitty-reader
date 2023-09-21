package com.cutedomain.kittyreader.screens


import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.res.painterResource
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
import com.cutedomain.kittyreader.screens.navigation.AppScreens
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoginScreen(navController: NavController){
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
            }, colors = TopAppBarDefaults.smallTopAppBarColors(containerColor=Color(0xFFFF72A2)
            ),
                navigationIcon = {
                    IconButton(onClick = { navController.navigate(AppScreens.LibraryScreen.route) }) {
                       Icon(imageVector = Icons.Filled.Book,
                           contentDescription = "Go to library",
                           tint= colorResource(id = R.color.white))
                    }
                }
            )
        }
    ) {
        LoginForm(navController)
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginForm(navController: NavController){
    val context= LocalContext.current
    Column(
        modifier= Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFFFF)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
        ) {
        // Variable email
        var email by rememberSaveable {
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
            // Email input
            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email", style = TextStyle.Default.copy(fontSize = 14.sp)) },
                leadingIcon = {
                    Icon(imageVector= Icons.Filled.Email, contentDescription = "Email input")
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
                onClick = { SignIn(
                    email = email,
                    pass= pass,
                    context = context,
                    navController = navController) },
                contentPadding = PaddingValues(
                    start = 20.dp,
                    top = 12.dp,
                    end = 20.dp,
                    bottom = 12.dp
                ),
                colors = ButtonDefaults.elevatedButtonColors(containerColor= colorResource(id = R.color.main_color))
            ) {
                Text(text = "Iniciar Sesión",style= TextStyle(color= colorResource(id = R.color.white)))
            }
            Spacer(modifier = Modifier.height(15.dp))
            val linkRegistrar = buildAnnotatedString {
                withStyle(style= SpanStyle(color=Color(0xFF111111) )){
                    append("¿No tienes una cuenta?, ")
                }
                pushStringAnnotation(
                    "register",
                    annotation = "https://google.com"
                )
                withStyle(style = SpanStyle(color=Color(0xFFFF72A2))) {
                    append("Regístrate")
                }
                // What does the function
                pop()
            }
            // Texto tipo link
            ClickableText(
                text = linkRegistrar,
                onClick = {
                    navController.navigate(AppScreens.RegisterScreen.route)
                },
                modifier= Modifier.padding(PaddingValues(bottom=40.dp))
            )

            // Iniciar sesión con Google
            OutlinedButton(onClick = { SignInGoogle() },
                colors = ButtonDefaults.elevatedButtonColors(colorResource(id = R.color.transparent)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(PaddingValues(start = 50.dp, end = 50.dp,bottom=10.dp)),
                shape = RoundedCornerShape(50)
                ){
                Image(painter = painterResource(id = R.drawable.google),
                    contentDescription = null)
                Text(text = "Inicia sesión con Google",
                    modifier=Modifier.padding(PaddingValues(start=10.dp)),
                    style= TextStyle(color= colorResource(id = R.color.black))
                )
            }

            // Iniciar sesión con Facebook
            OutlinedButton(onClick = { SignInFacebook() },
                colors = ButtonDefaults.elevatedButtonColors(colorResource(id = R.color.transparent)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(PaddingValues(start = 50.dp, end = 50.dp)) ,
                shape = RoundedCornerShape(50)
                ) {
                Image(painter =painterResource(id = R.drawable.facebook),
                    contentDescription = null)
                Text(text = "Inicia sesión con Facebook",
                    modifier= Modifier
                        .padding(PaddingValues(start = 10.dp))
                        .fillMaxWidth(),
                    style= TextStyle(color= colorResource(id = R.color.black))
                )
            }
        }

    }
}

private fun SignInGoogle() {

}

private fun SignInFacebook() {

}

@Composable
@Preview
fun RegisterPreview(){
    LoginScreen(navController = rememberNavController())
}

// Logics


// Mostrar diálogo de error
fun ShowErr(context: Context, message: String) {
    val builder=AlertDialog.Builder(context)
    builder.setTitle("Error")
    builder.setMessage(message)
    builder.setPositiveButton("Aceptar", null)
    val dialog: AlertDialog = builder.create()
    dialog.show()
}

// Iniciar sesión
// Migrar a un controlador
private fun SignIn(email: String, pass: String, context: Context, navController: NavController){
    if (email.isNotEmpty() && pass.isNotEmpty()){
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email,pass).
        addOnCompleteListener{
            if (it.isSuccessful){
                navController.navigate(AppScreens.LibraryScreen.route)
            }else ShowErr(context, "El usuario ingresado no es válido, vuelve a intentarlo.")
        }
    }
    else{
        ShowErr(context, "Los campos no pueden estar vacíos, por favor intenta otra vez.")
    }
}
