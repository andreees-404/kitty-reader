package com.cutedomain.kittyreader.domain.controllers

import android.content.Context
import android.content.Intent
import com.cutedomain.kittyreader.views.MainActivity
import com.cutedomain.kittyreader.views.EpubActivity
import com.cutedomain.kittyreader.views.PdfActivity
import com.cutedomain.kittyreader.domain.UploadActivity

@Suppress("IMPLICIT_CAST_TO_ANY")
class FileHandler {


    private companion object{
        private val TAG: String = "FILE_HANDLER"
    }
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

    internal fun UploadBook(context: Context){
        val uploadInent = Intent(context, UploadActivity::class.java).also {
            context.startActivity(it)
        }
    }




}