package com.cutedomain.kittyreader.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PersonPin
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.cutedomain.kittyreader.R
import com.cutedomain.kittyreader.models.DataProvider

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeLibrary(navController: NavController){
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),

        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFFFF72A2),
                    titleContentColor = Color(0xFFFFFFFF),
                ),
                title = {
                    Text(
                        "Centered Top App Bar",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = "Localized description",
                            tint=Color(0xFF111111)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            imageVector = Icons.Filled.PersonPin,
                            contentDescription = "Account menu",
                            tint = Color(0xFF111111)
                        )
                    }
                },
                scrollBehavior = scrollBehavior

            )
        },
        floatingActionButton = {
            IconButton(onClick = { /*TODO*/ }) {
               Icon(imageVector=Icons.Filled.Add , contentDescription = "Add a book ")
            }
        }
    ) {
        HomeLibrary(navController = navController)
    }


}

@SuppressLint("RememberReturnType")
@Composable
fun LibraryContent(navController: NavController){
    val books=remember { DataProvider.bookList }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFFFF))
            .padding(PaddingValues(top = 120.dp))
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally) {

        Card(
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            colors = CardDefaults.cardColors(Color(0xFFFFBDE9)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Row {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = "Example Icon",
                    modifier = Modifier.align(Alignment.CenterVertically)
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
                        Text(text = "Tittle: ",
                            style= TextStyle(color=Color(0xFF999999)),
                            modifier=Modifier.padding(10.dp),
                            fontStyle=FontStyle.Italic
                        )
                        Text(text = "Author: ",
                            style=TextStyle(color=Color(0xFF999999)),
                            modifier=Modifier.padding(10.dp)
                        )
                        Text(text = "Date: ",
                            style= TextStyle(color=Color(0xFF999999)),
                            modifier = Modifier.padding(10.dp),
                            fontStyle=FontStyle.Italic
                        )
                    }

                }
            }
        }

    }

}

@Preview(showBackground = true)
@Composable
fun HomePreview(){
    HomeLibrary(rememberNavController())
}