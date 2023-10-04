package com.cutedomain.kittyreader

import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.cutedomain.kittyreader.databinding.ActivityPdfBinding
import com.cutedomain.kittyreader.domain.controllers.FileHandler

class PdfActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPdfBinding
    private val reader = FileHandler()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPdfBinding.inflate(layoutInflater)

        setContentView(binding.root)

        launchPdf()

    }



    private fun launchPdf(){
        try {
            val launcher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
                if (uri != null) {
                    uri?.let {
                        binding.pdfView.fromUri(it).onError {
                         error -> println("Ha sucedido un error: $error")

                        }.load()

                    }
                } else {
                    println("!El usuario no ha seleccionado ningún archivo...")
                    reader.noSuchFile(context = this)
                    finish()
                }
            }
            launcher.launch("application/pdf")
        } catch (e: Exception){
            e.printStackTrace()
        }
    }
}