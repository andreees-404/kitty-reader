package com.cutedomain.kittyreader.domain;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.cutedomain.kittyreader.R;
import com.cutedomain.kittyreader.models.Book;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Objects;

public class UploadActivity extends AppCompatActivity {


    public static final String TAG = "Upload_Activity";
    StorageReference storage;
    DatabaseReference db;

    String bookTitle;
    String format;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        
        storage = FirebaseStorage.getInstance().getReference();
        db = FirebaseDatabase.getInstance().getReference("ebooks");


        Log.d(TAG, "onCreate: Loading Bundle");
        Bundle titleBundle = getIntent().getExtras();
        if (titleBundle != null) {
            bookTitle = titleBundle.get("format").toString();
            Log.d(TAG, "onCreate: Title: ");
        }


       Intent intent = getIntent();
       Bundle formatBundle = intent.getExtras();
       if (formatBundle != null) {
           format = formatBundle.get("format").toString();
           Log.d(TAG, "onCreate: Format: " + format);
       }

       selectFile();
    }


//    private void selectPdf(){
//        Log.d(TAG, "selectPdf: Selecting Pdf file");
//        Intent intent = new Intent();
//        intent.addCategory(Intent.CATEGORY_OPENABLE);
//        intent.setType("application/pdf");
//            intent.setAction(Intent.ACTION_GET_CONTENT);
//            startActivityForResult(Intent.createChooser(intent, "Please select pdf files"), 1);
//
//    }
//

        private void selectFile(){
        Log.d(TAG, "selectEpub: Selecting Epub file");
        Intent intent = new Intent();
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("application/epub+zip");
        String[] mimeTypes = {"application/epub+zip", "application/pdf"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        startActivityForResult(Intent.createChooser(intent, "Please select book files"), 1);

    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null){
            uploadBook(data.getData());
        }
    }

    private void uploadBook(Uri data){
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading");
        progressDialog.show();

        StorageReference storageReference = storage.child("ebooks/" + System.currentTimeMillis() + ".pdf");
        storageReference.putFile(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete()){
                    Uri url = uriTask.getResult();
                    progressDialog.dismiss();
                    Toast.makeText(UploadActivity.this, "File uploaded!", Toast.LENGTH_SHORT).show();

                    Book ebook = new Book(bookTitle, url.toString());
                }
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                double progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                progressDialog.setMessage("Uploaded " + (int)progress + "%");
            }
        });
    }
}