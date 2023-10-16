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
import android.speech.tts.TextToSpeech;

import com.folioreader.Config;
import com.folioreader.FolioReader;
import com.folioreader.mediaoverlay.MediaController;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class ReaderController {
    private Context context;
    private TextToSpeech tts;


    public ReaderController(Context con){
        this.context=con;
        }
    public void readBook(String filename) {
        AssetManager assetManager = context.getAssets();
        try {
            InputStream epubInputStream = assetManager.open(filename);
            File epubFile=createTempFile(epubInputStream);
            String name=epubFile.getName();
            System.out.println(name);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }



    // Ver el contenido de un archivo
    public String readContent(Context context, String filename){
        AssetManager assetManager=context.getAssets();
        try {
            InputStream inputStream=assetManager.open(filename);
            byte[] buffer=new byte[inputStream.available()];
            inputStream.read(buffer);
            System.out.println("Contenido cargado");
            // Convertirlo en una cadena de texto
            return new String(buffer, StandardCharsets.UTF_8);
        }catch (IOException ex){
            ex.printStackTrace();
            return null;
        }
    }


    // Convertir el archivo en uno temporal con una ruta válida
    private File createTempFile(InputStream inputStream) throws IOException{
        File tmp=File.createTempFile("epub_tmp", ".epub", context.getCacheDir());
        tmp.deleteOnExit();
        try (FileOutputStream outputStream=new FileOutputStream(tmp)){
            byte[] buffer= new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) > 0 ){
                outputStream.write(buffer, 0, bytesRead);
            }

            outputStream.close();
        }
        inputStream.close();
        return tmp;
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
