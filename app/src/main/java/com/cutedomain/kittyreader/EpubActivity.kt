package com.cutedomain.kittyreader

import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.cutedomain.kittyreader.domain.controllers.FileHandler
import com.folioreader.FolioReader
import java.io.File

class EpubActivity : AppCompatActivity() {
    lateinit var folioReader: FolioReader
    private val controller = FileHandler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        folioReader = FolioReader.get()
        loadEpub()
    }

    private fun loadEpub(){
        try {
            val launcher = registerForActivityResult(ActivityResultContracts.GetContent()){
                uri -> if (uri != null){
                uri.let {
                    val path = uri.path.toString()
                    openEpub(path)
                    println("Se ha abierto el archivo correctamente??")
                }
                }else{
                    println("!El usuario no ha seleccionado ningún archivo EPUB...")
                }
            }

            // Abrir el archivo
            launcher.launch("application/*")

        } catch (e: Exception) {
            controller.noSuchFile(this)
            finish()
        }
    }

    private fun openEpub(path: String) {
        if (File(path).exists()){
            // El archivo existe
            println(path)
            folioReader.openBook(path)

        }else {
            println("La ruta es inválida $path")
        }
    }


}