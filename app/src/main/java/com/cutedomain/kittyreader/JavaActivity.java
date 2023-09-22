package com.cutedomain.kittyreader;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class JavaActivity extends AppCompatActivity {
    // for this activity, declare <style> tag App.Compat
    // in the Android Manifest File
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java);
    }
}