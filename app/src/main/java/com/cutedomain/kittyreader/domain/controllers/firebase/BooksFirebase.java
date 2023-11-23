package com.cutedomain.kittyreader.domain.controllers.firebase;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.cutedomain.kittyreader.models.EBook;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;


public class BooksFirebase {
    FirebaseStorage storage;
    Uri imageUri = null;
    private Context context;
    StorageReference mStorageRef;
    private static final String TAG = "BooksFirebase";


    /*
    * @Test Solo funcionaría con un libro pasando un nombre
    * Obtener los libros subidos a firebase
    *
    * @param filename Nombre del arhcivo*/
    public void getBooks(String filename){
        mStorageRef = storage.getInstance().getReference("books/" + filename + ".epub");
        try {
            File temp = File.createTempFile("temp_book", ".epub");
            mStorageRef.getFile(temp)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Log.d(TAG, "onSuccess: Load file to LazyColumn");

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, "onFailure: Failed to retireve data!");
                        }
                    });
        } catch (IOException e) {
            Log.d(TAG, "getBooks: Failed!" + e.getMessage());
        }

    }


    public void getAllBooks(){
        mStorageRef = storage.getReference("books/");

    }


}
