package com.cutedomain.kittyreader.screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.cutedomain.kittyreader.R
import com.cutedomain.kittyreader.models.DataProvider


// Notes : Set lezycolumn into LibraryScreen function on Scaffold giving innerPadding parameter 
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryScreen(navController: NavController){
    val context = LocalContext.current // Contexto actual
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    // Ordenarndo un poco los elementos
    Scaffold(
        modifier=Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        // Barra superior
        topBar = { TopAppBar(title = { Text(text = "Library",
                style = TextStyle(color= colorResource(id = R.color.white), fontSize = 24.sp), maxLines = 1, overflow = TextOverflow.Ellipsis) },
            colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = colorResource(id = R.color.main_color)),
            navigationIcon = {
                IconButton(onClick = { Toast.makeText(context, "Barra lateral desplegada??", Toast.LENGTH_SHORT).show() }) {
                    Icon(imageVector = Icons.Filled.Menu, contentDescription = "Lateral menu", tint= colorResource(id = R.color.white))
                }
            },
            actions = {}
            )},
            content = { innerPadding ->
                BookListBody(navController,paddingValues = innerPadding)

            }
        )

}


@Composable
fun BookListBody(navController: NavController, paddingValues: PaddingValues){
    val books=DataProvider.bookList

    val length=books.size
    LazyColumn(

    ){
        items(books){
            book -> BookCard(
                isbn = book.isbn,
                title = book.title,
                author = book.author,
                date = book.date,
                category = book.category,
                image = book.image
        )
        }
    }
}


@Composable
fun BookCard(
    isbn: String,
    title: String,
    author: String,
    date: String,
    category: String,
    image: Int
){
    val bookId=isbn
    val bookCategory=category
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(Color(0xFFDBB9D0)),
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ){
        Row {
            Image(
                painter= painterResource(id = image),
                contentDescription = "Example Icon",
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .size(100.dp)
                    .padding(5.dp),
                contentScale= ContentScale.Crop
            )
            Column(
                modifier=Modifier.padding(PaddingValues(top=5.dp))
            ){
                Card(
                    modifier= Modifier
                        .padding(10.dp)
                        .fillMaxWidth(),
                    colors = CardDefaults.cardColors(Color(0xFFFFE2EC))
                ) {
                    Text(text = "Tittle: $title",
                        style= TextStyle(color= Color(0xFF999999)),
                        modifier=Modifier.padding(10.dp),
                        fontStyle= FontStyle.Italic
                    )
                    Text(text = "Author: $author",
                        style=TextStyle(color= Color(0xFF999999)),
                        modifier=Modifier.padding(10.dp)
                    )
                    Text(text = "Date: $date",
                        style= TextStyle(color= Color(0xFF999999)),
                        modifier = Modifier.padding(10.dp),
                        fontStyle= FontStyle.Italic
                    )
                }

            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun HomePreview(){
    LibraryScreen(rememberNavController())
}