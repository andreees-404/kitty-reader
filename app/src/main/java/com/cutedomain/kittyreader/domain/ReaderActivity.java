package com.cutedomain.kittyreader.domain;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.cutedomain.kittyreader.R;
import com.folioreader.Config;
import com.folioreader.FolioReader;
import com.folioreader.model.locators.ReadLocator;
import com.folioreader.util.AppUtil;

public class ReaderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        openEpub();
    }


    private void openEpub(){
        FolioReader reader = FolioReader.get();
       reader.openBook("file///android_asset/El_libro_de_Enoc-Anonimo.epub");

    }
}