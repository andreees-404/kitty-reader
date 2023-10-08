package com.cutedomain.kittyreader.domain.controllers

import android.content.Context
import android.content.Intent
import com.cutedomain.kittyreader.EpubActivity
import com.cutedomain.kittyreader.MainActivity
import com.cutedomain.kittyreader.PdfActivity
import com.folioreader.FolioReader

class FileHandler {
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


    internal fun noSuchFile(context: Context){
        val backIntent = Intent(context, MainActivity::class.java).also {
            context.startActivity(it)
        }

    }
}