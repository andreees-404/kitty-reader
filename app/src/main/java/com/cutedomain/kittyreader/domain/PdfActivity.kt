package com.cutedomain.kittyreader.domain

import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.cutedomain.kittyreader.databinding.ActivityPdfBinding
import com.cutedomain.kittyreader.domain.controllers.FileHandler

class PdfActivity : AppCompatActivity() {

    private companion object {
    private val TAG: String = "PDF_ACTIVITY"

    }
    private lateinit var binding: ActivityPdfBinding

    private val filesController = FileHandler()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPdfBinding.inflate(layoutInflater)

        setContentView(binding.root)

        launchPdf()

    }


    /*
    * Cargar un archivo PDF en una vista,
    * esta función permite al usuario elegir
    * un archivo de formato .pdf en el explorador de archivos
    */
    private fun launchPdf(){
        try {
            val launcher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
                if (uri != null) {
                    uri?.let {
                        binding.pdfView.fromUri(it).onError {
                         error -> println("An error was ocurred!: $error")
                        }.load()
                    }
                } else {
                    Log.d(TAG, "launchPdf: no file selected!")
                    filesController.noSuchFile(context = this)
                    finish()
                }
            }
            launcher.launch("application/pdf")
        } catch (e: Exception){
            Log.d(TAG, "${e.message}")
            filesController.noSuchFile(this)
            finish()
        }
    }
}