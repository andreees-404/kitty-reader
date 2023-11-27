package com.cutedomain.kittyreader.views

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.cutedomain.kittyreader.databinding.ActivityPdfBinding
import com.cutedomain.kittyreader.domain.utils.FileHandler

class PdfActivity : AppCompatActivity() {

    private companion object {
    private val TAG: String = "PdfActivity"

    }

    private lateinit var binding: ActivityPdfBinding

    private val filesController = FileHandler()
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPdfBinding.inflate(layoutInflater)

        //var currentPage: Int? = getLastPage()

            setContentView(binding.root)
            launchPdf(1)


    }


    /*
    * Cargar un archivo PDF en una vista,
    * esta función permite al usuario elegir
    * un archivo de formato .pdf en el explorador de archivos
    */
    private fun launchPdf(page: Int){
        try {
            val launcher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
                if (uri != null) {
                    uri?.let {
                        binding.pdfView.fromUri(it).onError {
                         error -> println("An error was ocurred!: $error")
                        }.defaultPage(page).
                        enableDoubletap(true).
                        swipeHorizontal(true).
                        autoSpacing(true).
                        pageSnap(true).pageFling(true).load()
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

    /* Obtener la última página
    *  que el usuario vió del Pdf
    */
    //private fun getLastPage(): Int{
    //    // Hacer una consulta a la base de datos que obtenga la página
    //    // Cada vez que el usuario cambie de página, el dato se envía
    //    // Implementar un Gesture detector
    //    // Implementar los marcadores
    //}
}