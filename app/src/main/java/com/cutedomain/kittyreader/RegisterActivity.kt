package com.cutedomain.kittyreader

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.CheckBox
import android.widget.ImageButton
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cutedomain.kittyreader.ui.theme.KittyReaderTheme
import com.cutedomain.kittyreader.ui.theme.onBackground
import com.google.android.material.appbar.AppBarLayout


class RegisterActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KittyReaderTheme {
                // A surface container using the 'background' color from the theme
                Surface {
                    AppBar()
                }
            }
        }
    }


    // Validate password functions
//private fun valPass(pass: String): Boolean =pass.matches(Regex("[!@#\$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]"))
    private fun valLength(pass: String): Boolean = pass.matches(Regex(".{6,}"))


    @SuppressLint("ComposableNaming", "UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun AppBar() {

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
                    colors = TopAppBarDefaults.smallTopAppBarColors(Color(0xFFFF72A2))
                )
            }
        ) {
            Box(
                Modifier
                    .fillMaxSize()
                    .background(Color(0xFFFFFFFF)),
            ) {
                RegisterForm()
            }
        }
    }

    @Preview
    @Composable
    fun AppPreview() {
        AppBar()
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun RegisterForm() {

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
                            modifier = Modifier.align(CenterVertically)
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
