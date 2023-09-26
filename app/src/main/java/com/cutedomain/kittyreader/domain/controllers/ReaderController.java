package com.cutedomain.kittyreader.domain.controllers;

import static androidx.browser.customtabs.CustomTabsClient.getPackageName;
import static androidx.core.app.ActivityCompat.startActivityForResult;
import static androidx.core.content.ContextCompat.startActivity;

import static java.nio.channels.FileChannel.open;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.provider.DocumentsContract;

import com.cutedomain.kittyreader.R;
import com.folioreader.FolioReader;

import java.io.IOException;
import java.io.InputStream;

public class ReaderController {
    private Context context;
    // public int _id=context.getResources().getIdentifier("El_libro_de_Enoc-Anonimo", "raw", context.getPackageName());
    // En esta clase se definirán todos los métodos
    // con los que manejaremos los archivos de los libros

    FolioReader reader;

    public ReaderController(Context con){
        this.context=con;
    }
    public void readBook() {
        AssetManager assetManager = context.getAssets();
        try {
            InputStream epubInputStream = assetManager.open("El_libro_de_Enoc-Anonimo.epub");
            reader = FolioReader.get();
            reader.openBook(epubInputStream.toString());
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }


    private static final int PICK_PDF_FILE = 2;

    private void openFile(Uri pickerInitialUri) {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("application/pdf");

        // Optionally, specify a URI for the file that should appear in the
        // system file picker when it loads.
        intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, pickerInitialUri);

        context.startActivity(intent);
    }



}
