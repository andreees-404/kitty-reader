package com.cutedomain.kittyreader.screens.library

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DismissibleDrawerSheet
import androidx.compose.material3.DismissibleNavigationDrawer
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.cutedomain.kittyreader.R
import com.cutedomain.kittyreader.domain.controllers.StoragePermissionsActivity
import com.cutedomain.kittyreader.domain.utils.UserUtils
import com.cutedomain.kittyreader.models.data.items
import com.cutedomain.kittyreader.models.data.itemsLogged
import com.cutedomain.kittyreader.screens.navigation.AppScreens
import com.cutedomain.kittyreader.viewmodel.BookViewModel
import com.cutedomain.kittyreader.viewmodel.UserViewModel
import kotlinx.coroutines.launch







val userUtils = UserUtils()
/*
* Pantalla principal de la librería
*
* @param navController
*   Componente necesario para la navegación
*/
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryScreen(navController: NavController){

    // ViewModel
    val userViewModel: UserViewModel = viewModel<UserViewModel>()

    // Email by ViewModels
    val vmEmail by userViewModel.email.observeAsState("Anónimo")
    // Local context
    val context = LocalContext.current

    // Scafold state -> Migration from ScaffoldState
    val snackbarHostState = remember { SnackbarHostState()}
    // Escupo
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    var isOpen = false
    var selectedItemIndex by rememberSaveable {
        mutableStateOf(0)
    }
    // Menú lateral
    DismissibleNavigationDrawer(drawerContent = {
        DismissibleDrawerSheet {
            Column(
                modifier= Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp), verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(imageVector = Icons.Default.Person, contentDescription = null, modifier = Modifier.size(48.dp))
                            Text(text = vmEmail, fontWeight = FontWeight.Bold)
                    }
            }
            Spacer(modifier = Modifier.height(16.dp))

            if(vmEmail == "Anónimo"){
            // Iteramos en la lista de elementos
            items.forEachIndexed { index, item ->
                NavigationDrawerItem(
                    label = { Text(text = item.title) },
                    selected = index == selectedItemIndex,
                    onClick = {
                        selectedItemIndex = index
                        scope.launch { drawerState.close() }
                        navController.navigate(item.route)
                    },
                    icon = {
                        Icon(
                            imageVector = if (index == selectedItemIndex) item.selectedIcon else item.unSelectedIcon,
                            contentDescription = item.title
                        )
                    },
                    badge = {
                        item.badgeCount?.let { Text(text = item.badgeCount.toString()) }
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )
            }
            } else {
                itemsLogged.forEachIndexed { index, item ->
                    NavigationDrawerItem(
                        label = { Text(text = item.title) },
                        selected = index == selectedItemIndex,
                        onClick = {
                            selectedItemIndex = index
                            scope.launch { drawerState.close() }
                            navController.navigate(item.route)
                        },
                        icon = {
                            Icon(
                                imageVector = if (index == selectedItemIndex) item.selectedIcon else item.unSelectedIcon,
                                contentDescription = item.title
                            )
                        },
                        badge = {
                            item.badgeCount?.let { Text(text = item.badgeCount.toString()) }
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }

                Column(
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(onClick = { userUtils.signOut(context) }) {
                    Text(text = "Cerrar Sesión")
                }

                }
            }
        }
    },
        drawerState=drawerState
    ) {
        // Contenedor principal
        Scaffold(
            topBar = {
                AppBar(
                    onClick = {
                        // Hacer click en el menú
                        scope.launch {
                            isOpen = !isOpen
                            if (isOpen){
                                drawerState.open() } else { drawerState.close() }
                        }
                    }
                )
            },
            floatingActionButton = {
                AddButton {

                    //val intent = Intent(context, EpubActivity::class.java).also {
                    //    context.startActivity(it)
                    //}
                    navController.navigate(AppScreens.ChooseFile.route)
                }
            }
        ) {
                innerPadding ->
            BookList(context = context, innerPadding = innerPadding)
        }
    }
}


/*
* Muestra los libros en la pantalla
*
* @param context
*   Contexto de la activity para aplicar
*   funcionalidades como un Toast.makeText()
*
* @test
* @param books
*   Lista de libros para mostrarlos en la vista
*
*  @param innerPadding
*   Padding proporcionado por la columna principal
*/
@Composable
fun BookList(context: Context, innerPadding: PaddingValues) {
    // ViewModel
    val bookViewModel = BookViewModel(LocalContext.current)

    val vmBooks by bookViewModel.books.observeAsState(emptyList())

    Image(painter = painterResource(id = R.drawable.kittybanner),
        contentDescription = "Kitty Banner",
        modifier= Modifier
            .padding(innerPadding)
            .fillMaxWidth() )
    LazyColumn(
        modifier= Modifier
            .fillMaxSize()
            .padding(paddingValues = PaddingValues(top = 250.dp))
    ) {
        vmBooks.forEach{ bookItem ->
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
                                    "Formato:  ${bookItem.format}",
                                    Toast.LENGTH_SHORT
                                )
                                .show()
                            openBook(context, bookItem.format)
                        },
                    colors=CardDefaults.cardColors(colorResource(id = R.color.card_description)),
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    BookCard(
                        title = bookItem.title,
                        author = bookItem.author,
                        image = R.drawable.el_libro_de_enoc_anonimo_lg
                        )
                }
            }
        }
    }
}

private fun openBook(context: Context, format: String) {
   Intent(context, StoragePermissionsActivity::class.java).also{
       it.putExtra("type", format)
       context.startActivity(it)
   }
}


/*
* Presentación del libro en una tarjeta
*
* @param isbn
*   Identificador del libro
*
* @param title
*   Título del libro
*
* @param author
*   Autor del libro
*
* @param date
*   Fecha de publicación del libro
*
* @param category
*   Categoría del libro
*
* @param image
*   Imagen representado en Integer -> R.drawable.image_example
*/
@Composable
fun BookCard(title: String, author: String, image: Int) {

    // Book progress
    val currentProgress by remember { mutableStateOf(0f) }
    Row {
        Column(
            modifier=Modifier.background(colorResource(id = R.color.white))
        ) {
            Image(painter = painterResource(id = image),
                contentDescription = null,
                modifier= Modifier
                    .padding(5.dp)
                    .size(80.dp))
        }
        Column(
            modifier= Modifier
                .padding(
                    paddingValues = PaddingValues(
                        start = 10.dp,
                        top = 5.dp,
                        end = 5.dp,
                        bottom = 5.dp
                    )
                )
                .fillMaxSize()
        ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(text = title, fontSize = 18.sp, style= TextStyle(color= colorResource(id = R.color.black)))
                    Text(
                        text = author,
                        fontSize = 14.sp, style = TextStyle(color = colorResource(id = R.color.gray)
                        ),
                        fontStyle = FontStyle.Italic
                    )
                    Box(
                        modifier=Modifier.padding(PaddingValues(top=25.dp)),
                        contentAlignment = Alignment.BottomStart){

                        LinearProgressIndicator(
                            modifier=Modifier.fillMaxWidth(),
                            progress = currentProgress
                        )
                    }
                  }

        }
    }
}


/*
* Barra de navegación de la pantalla
* de libros (posbile migración a otro fichero)
*
* @param onClick
*   Función para implementar la lógica de tocar
*   el ícono de navegación
*/
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(onClick: () -> Unit){
    CenterAlignedTopAppBar(
        title = { Text(
            text = stringResource(id = R.string.app_name),
            maxLines = 1,
            fontSize=24.sp,
            style = TextStyle(
                color= colorResource(
                    id = R.color.white))
        )},
        navigationIcon = {
            IconButton(onClick = { onClick() }) {
               Icon(imageVector = Icons.Filled.Menu, contentDescription = null, tint= colorResource(
                   id = R.color.white))
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(colorResource(id = R.color.main_color))
    )
}

/*
* Agregar libros
* desde el almacenamiento interno del sistema
*
* @param onClick
*   Función a implementar en el
*   evento al presionar el botón
*/
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

/*
* Preview de la app
*/
@Preview(showBackground = true)
@Composable
fun BookListPreview(){
    LibraryScreen(navController = rememberNavController())
}
