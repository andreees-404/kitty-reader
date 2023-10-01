package com.cutedomain.kittyreader;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PackageManagerCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.cutedomain.kittyreader.domain.controllers.FileHandler;
// import com.cutedomain.kittyreader.domain.controllers.ReaderController;

import org.w3c.dom.Text;

import java.util.Locale;

public class JavaActivity extends AppCompatActivity {
    private static final int STORAGE_PERMISSION_CODE = 100;
    // ReaderController reader=new ReaderController(this);
    private TextToSpeech tts;
    int REQUEST_CODE = 100;


    // for this activity, declare <style> tag App.Compat
    // in the Android Manifest File
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java);

        Button button_open_book = findViewById(R.id.btn_read);
        button_open_book.setOnClickListener(view -> {
            //reader.readBook("El_libro_de_Enoc-Anonimo.epub");
        });
    }


    private void openBook() {
        Toast.makeText(this, "Abriendo libro...", Toast.LENGTH_SHORT).show();
    }

    public void fileSearch() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("pdf/*");
        startActivity(intent);
    }

}