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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
/*
public class ReaderController {
    private Context context;
    private TextToSpeech tts;
    // public int _id=context.getResources().getIdentifier("El_libro_de_Enoc-Anonimo", "raw", context.getPackageName());
    // En esta clase se definirán todos los métodos
    // con los que manejaremos los archivos de los libros

    FolioReader reader=FolioReader.get();
    Config config;

    MediaController mediaController;

    public ReaderController(Context con){
        this.context=con;
        }
    public void readBook(String filename) {
        AssetManager assetManager = context.getAssets();
        try {
            InputStream epubInputStream = assetManager.open(filename);
            File epubFile=createTempFile(epubInputStream);
            String name=epubFile.getName();
            config=readerConfig();
            System.out.println(name);
            reader.openBook("file:///android_assset/El_libro_de_Enoc-Anonimo.epub");
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public Config readerConfig() {
        config=new Config();
        config.setShowTts(false);
        config.setNightMode(true);
        config.setFontSize(20);
        return config;
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
*/