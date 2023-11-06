package com.cutedomain.kittyreader.screens

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.cutedomain.kittyreader.R
import com.cutedomain.kittyreader.domain.EpubActivity
import com.cutedomain.kittyreader.domain.PdfActivity
import com.cutedomain.kittyreader.models.DataProvider
import com.cutedomain.kittyreader.models.Formats

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChooseFile(navController: NavHostController) {
    Scaffold{
            FormatList(context = LocalContext.current, formats = DataProvider.bookFormats)
        }
    }



@Composable
fun FormatList(context: Context, formats :List<Formats>){
    Column(
        modifier = Modifier.fillMaxWidth().padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Selecciona el formato de archivo que quieres añadir",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            style = TextStyle(color = colorResource(R.color.grey))
        )
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(PaddingValues(start = 10.dp, end = 10.dp, top = 100.dp, bottom = 50.dp))
    ){
        formats.forEach{
            format ->
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(8.dp)
                        .clickable {
                            Toast
                                .makeText(context, "${format.name}", Toast.LENGTH_SHORT)
                                .show()

                            selectFile(context, format.name)
                        },
                    colors = CardDefaults.cardColors(colorResource(id = R.color.card_description))
                ){
                    FormatCard(
                        description = format.description,
                        image = format.image
                    )
                }
            }
        }
    }
}

private fun selectFile(context: Context, name: String) {
    if (name == "PDF"){
        //Intent(context, UploadActivity::class.java).also {
        //    it.putExtra("format", name)
        //    context.startActivity(it)
        //}
        Intent(context, PdfActivity::class.java).also {

        }
    }
    else if(name == "EPUB"){
        //Intent(context, UploadActivity::class.java).also {
        //    it.putExtra("format", name)
        //    context.startActivity(it)
        //}
        Intent(context, EpubActivity::class.java).also {
            context.startActivity(it)
        }
    }
}

@Composable
fun FormatCard(description: String, image: Int) {
   Row {
       Column(
           modifier = Modifier.padding(10.dp),
           horizontalAlignment = Alignment.CenterHorizontally,
           verticalArrangement = Arrangement.Center
       ) {
           Image(painter = painterResource(id = image), contentDescription = null, modifier = Modifier
               .size(128.dp)
               .padding(8.dp))
       }
       Column {
            Text(modifier = Modifier.padding(PaddingValues(top = 20.dp)), text = description, style = TextStyle(color = colorResource(id = R.color.grey)))
       }
          }
}

@Preview (showBackground = true)
@Composable
fun MainPreview(){
    ChooseFile(navController = rememberNavController())
}
