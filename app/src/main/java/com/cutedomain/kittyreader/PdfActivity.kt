package com.cutedomain.kittyreader

import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.cutedomain.kittyreader.databinding.ActivityPdfBinding
import com.cutedomain.kittyreader.domain.controllers.FileHandler

class PdfActivity : AppCompatActivity() {

    private val TAG: String = "PDF_ACTIVITY"
    private lateinit var binding: ActivityPdfBinding
    private val reader = FileHandler()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPdfBinding.inflate(layoutInflater)

        setContentView(binding.root)

        launchPdf()

    }


    /*
    * Esta función carga un pdf
    * con la llamada de un launcher para seleccionar un archivo
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
                    reader.noSuchFile(context = this)
                    finish()
                }
            }
            launcher.launch("application/pdf")
        } catch (e: Exception){
            Log.d(TAG, "${e.message}")
            reader.noSuchFile(this)
            finish()
        }
    }
}