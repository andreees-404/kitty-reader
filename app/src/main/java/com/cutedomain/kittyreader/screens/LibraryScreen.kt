package com.cutedomain.kittyreader.screens

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.cutedomain.kittyreader.R
import com.cutedomain.kittyreader.models.Book
import com.cutedomain.kittyreader.models.DataProvider

// Notes : Set LazyColumn into LibraryScreen function on Scaffold giving innerPadding parameter
// Lista principal
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryScreen(navController: NavController){
    // Local context
    val context= LocalContext.current

    // Contenedor principal
    Scaffold(
        topBar = {},
        floatingActionButton = { AddButton{
            Toast.makeText(context, "Add a book", Toast.LENGTH_SHORT).show()
        } }
    ){
        innerPadding -> BookList(context = context, books = DataProvider.bookList, innerPadding = innerPadding)
    }
}


// Lista de libros
@Composable
fun BookList(context: Context, books: List<Book>, innerPadding: PaddingValues) {
    LazyColumn(
        modifier= Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        books.forEach{ bookItem ->
            item {
                Card(
                    modifier= Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .wrapContentHeight()
                        .clickable {
                            Toast
                                .makeText(
                                    context,
                                    "Click en card ${bookItem.isbn}",
                                    Toast.LENGTH_SHORT
                                )
                                .show()
                        } ,
                    colors=CardDefaults.cardColors(colorResource(id = R.color.backgorundMain)),
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    BookCard(
                        isbn = bookItem.isbn,
                        title = bookItem.title,
                        author = bookItem.author,
                        date = bookItem.date,
                        category = bookItem.category,
                        image =bookItem.image
                    )
                }
            }
        }
    }
}


// Tarjeta de libro
@Composable
fun BookCard(isbn: String, title: String, author: String, date: String, category: String, image: Int
) {
    Row {
        Column(
            modifier=Modifier.background(colorResource(id = R.color.white))
        ) {
            Image(painter = painterResource(id = image),
                contentDescription = null,
                modifier= Modifier
                    .padding(5.dp)
                    .size(120.dp))
        }
        Column(
            modifier= Modifier
                .padding(
                    paddingValues = PaddingValues(
                        start = 20.dp,
                        top = 5.dp,
                        end = 5.dp,
                        bottom = 5.dp
                    )
                )
                .fillMaxSize()
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors=CardDefaults.cardColors(colorResource(
                    id = R.color.card_description))
            ) {
                Column(modifier = Modifier.padding(10.dp)) {
                    // Mejorar a un contenedor con varios textos
                    Text(text = title, fontSize = 18.sp, style= TextStyle(color= colorResource(id = R.color.black)), fontStyle = FontStyle.Italic)
                    Text(
                        text = "Author: $author", fontSize = 14.sp, style = TextStyle(
                            color = colorResource(
                                id = R.color.gray
                            )
                        ),
                        fontStyle = FontStyle.Italic
                    )
                }
            }

            // Espacio entre la barra de progreso y la descripción
            Spacer(modifier = Modifier.padding(PaddingValues(top = 30.dp)))

            // Barra de progreso
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Bottom
            ) {
                Text(text = "Progess bar here!")
                // ProgressBar()
            }
        }

    }
}


// AppBar
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(onClick: () -> Unit, context: Context){
    CenterAlignedTopAppBar(
        title = { Text(
            text = stringResource(id = R.string.app_name),
            maxLines = 1,
            style = TextStyle(
                color= colorResource(
                    id = R.color.white))
        )},
        navigationIcon = {
            IconButton(onClick = { Toast.makeText(context, "Desplegar barra lateral?", Toast.LENGTH_SHORT).show() }) {
               Icon(imageVector = Icons.Filled.Menu, contentDescription = null, tint= colorResource(
                   id = R.color.white))
            }
        }
    )
}

// Floating Action Button
@Composable
fun AddButton(onClick: () -> Unit){
    FloatingActionButton(
        onClick = { onClick()},
        shape = CircleShape,
        containerColor = colorResource(id = R.color.main_color)
        ) {
        Icon(imageVector = Icons.Filled.Add,contentDescription = null, tint = colorResource(id = R.color.white))
    }
}

// Preview
@Preview(showBackground = true)
@Composable
fun BookListPreview(){
    LibraryScreen(navController = rememberNavController())
}
