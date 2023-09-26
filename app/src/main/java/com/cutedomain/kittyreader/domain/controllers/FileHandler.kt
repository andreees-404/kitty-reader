package com.cutedomain.kittyreader.domain.controllers

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.DocumentsContract

class FileHandler {

    val PICK_PDF_FILE = 2
    public fun openFile(context: Context,pickerInitialUri: Uri){
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "application/*"
            putExtra(DocumentsContract.EXTRA_INITIAL_URI, pickerInitialUri)
        }
        context.startActivity(intent)
    }
}