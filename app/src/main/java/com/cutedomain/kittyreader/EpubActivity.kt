package com.cutedomain.kittyreader

import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.documentfile.provider.DocumentFile
import com.cutedomain.kittyreader.domain.OnFilePathRecivedListener
import com.cutedomain.kittyreader.domain.controllers.FileHandler
import com.folioreader.FolioReader

class EpubActivity : AppCompatActivity() {
    // Ruta del libro
    private lateinit var pathListener: OnFilePathRecivedListener
    private val TAG: String = "EPUB_ACTIVITY"
    private val APP_DIR: String = "kittyreader"
    private val EBOOKS_DIR: String = "ebooks"
    private lateinit var launcher: ActivityResultLauncher<String>
    private lateinit var folioReader: FolioReader

    private val handler: FileHandler = FileHandler()

    // Controlador de archivos
    private val controller = FileHandler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        pathListener = OnFilePathRecivedListener {
            if (it != null){
                Log.d(TAG, "onCreate: Opening book!")

                // Epub try
                openEpub(it)
            } else {
                Log.d(TAG, "onCreate: No such file or directory!")
                controller.noSuchFile(this)
            }
        }
        setPathRecived(pathListener)

        // Instanciar FolioReader
        folioReader = FolioReader.get()

        // Seleccionar el libro

        //selectBook()

    }



    private fun setPathRecived(listener: OnFilePathRecivedListener){
        this.pathListener = listener
    }


    /*
    * Abrir el libro con FolioReader
    * */
    private fun openEpub(bookPath: String) {
        try {
            folioReader.openBook(bookPath)
        } catch(e: Exception){
            Log.d(TAG, "openEpub: ${e.message}")
        }
    }

    /*
    * Esta función obtiene la ruta absoluta
    * del archivo que el usuario seleccione
    *
    * Abre el libro cuando a penas se seleccione
    * del explorador de archivos
    */
    private fun selectBook(){
        launcher = registerForActivityResult(ActivityResultContracts.GetContent(), ActivityResultCallback {
            uri -> if (uri != null){
                val documentFile = DocumentFile.fromSingleUri(this, uri)
                val filename = controller.getNameFromUri(this, uri)
                if (filename != null){
                    print("Filename -> $filename")
                    val bookPath = "${Environment.getExternalStorageDirectory().absolutePath}/$APP_DIR/$EBOOKS_DIR/$filename"
                    Log.d(TAG, "getPath: File: $bookPath")
                    pathListener.onFilePathRecivedListener(bookPath)
                }
            }
        })
        try {
            launcher.launch("*/*")
        } catch (e: Exception){
            Log.d(TAG, "getPath: ${e.message}")
            finish()
        }
    }


}