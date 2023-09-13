package com.cutedomain.kittyreader.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.cutedomain.kittyreader.R
import com.cutedomain.kittyreader.models.Book
import com.cutedomain.kittyreader.models.DataProvider
// Notes : Set LazyColumn into LibraryScreen function on Scaffold giving innerPadding parameter

// Lista principal
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryScreen(navController: NavController){
    Scaffold(
        topBar = {},
        content={ BookList(DataProvider.bookList) },
        floatingActionButton = {}
    )
}


// Lista de libros
@Composable
fun BookList(books: List<Book>) {
    LazyColumn(
        modifier=Modifier.fillMaxSize()
    ) {
        books.forEach{ bookItem ->
            item {
                Card(
                    modifier= Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .wrapContentHeight() ,
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


// Floating Action Button
@Composable
fun AddButton(){

}

// Preview
@Preview(showBackground = true)
@Composable
fun BookListPreview(){
    BookList(books = DataProvider.bookList)
}
