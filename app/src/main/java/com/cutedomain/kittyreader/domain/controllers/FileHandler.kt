package com.cutedomain.kittyreader.domain.controllers

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import com.cutedomain.kittyreader.EpubActivity
import com.cutedomain.kittyreader.MainActivity
import com.cutedomain.kittyreader.PdfActivity
import com.folioreader.FolioReader
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

@Suppress("IMPLICIT_CAST_TO_ANY")
class FileHandler {
    private val TAG: String = "FILE_HANDLER"
    // Pendiente : Arreglar problema al abrir arhivo epub
    private val folioreader = FolioReader.get()
    internal fun openPdf(context: Context){
        val intent = Intent(context, PdfActivity::class.java).also {
            context.startActivity(it)
        }
    }

    internal fun openEpub(context: Context){
        val intent = Intent(context, EpubActivity::class.java).also {
            context.startActivity(it)
        }
    }

    @SuppressLint("Range")
    internal fun getNameFromUri(context: Context, uri: Uri): String? {
        var fileName: String? = null
        val contentResolver = context.contentResolver

        val cursor = contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            it.moveToFirst()

            val displayName = it.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
            fileName = if (displayName != null) {
                displayName
            } else {
                val lastPathSegment = uri.lastPathSegment
                if (lastPathSegment != null) {
                    File(lastPathSegment).name
                } else {
                    null
                }
            }
        }
        return fileName

    }

    private fun copyFile(inputStream: InputStream, outputStream: OutputStream){
        val buffer = ByteArray(1024)
        var read: Int
        try {
            while (inputStream.read(buffer).also { read = it } != -1){
                outputStream.write(buffer, 0, read)
            }
            Log.d(TAG, "copyFile: File copied!")
        } catch (e: IOException){
            Log.d(TAG, "copyFile: Failed to copy File")
        }
    }


    internal fun noSuchFile(context: Context){
        val backIntent = Intent(context, MainActivity::class.java).also {
            context.startActivity(it)
        }

    }
}