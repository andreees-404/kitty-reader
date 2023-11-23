package com.cutedomain.kittyreader.domain.controllers.firebase;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.cutedomain.kittyreader.domain.utils.FileUtil;
import com.cutedomain.kittyreader.models.Book;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.slf4j.ILoggerFactory;

public class UploadActivity extends AppCompatActivity {


    public static final String TAG = "UploadActivity";
    private StorageReference storage;
    private FirebaseAuth mAuth;
    DatabaseReference db;

    String bookTitle;
    String format;

    private ActivityResultLauncher<String> launcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Firebase
        mAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance().getReference();
        db = FirebaseDatabase.getInstance().getReference("ebooks");


        // Obtener el formato de archivo
       Intent intent = getIntent();
       Bundle bundle = intent.getExtras();
       if (bundle != null) {
           format = bundle.get("format").toString();
           bookTitle = bundle.get("title").toString();

           Log.d(TAG, "onCreate: Format: " + format.toLowerCase());
           Log.d(TAG, "onCreate: Title: " + bookTitle);
       }

       FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {
            // Seleccionar un archivo a subir
            selectFile(format);
            Log.d(TAG, "onCreate: user: " + user.getDisplayName());
            Log.d(TAG, "onCreate: " + user.getIdToken(true));
        } else {
            signInAnonimously();
            selectFile(format);
        }
    }

    private void signInAnonimously() {
        mAuth.signInAnonymously().addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Log.d(TAG, "onSuccess: Ingreso anónimo exitoso");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: No se ha podido inicar anónimamente");
            }
        });
    }



    private void selectFile(String type){
        try {
            launcher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri o) {
                    if (o != null) {
                        // Enviar el libro a firebase
                        uploadBook(o);
                    }
                }
            });

            if (type == "pdf"){
                launcher.launch("application/pdf");
            } else if (type == "epub"){
                launcher.launch("application/epub+zip");
            }
        } catch (Exception e) {
            Log.d(TAG, "selectFile: Failed");
            e.printStackTrace();
        }
    }



    /* Arreglar la excepción que lanza al subir un archivo a Firebase
    *  el archivo se sube igualmente jaja
    *
    * @param data uri del archivo que seleccionamos para subir*/
    private void uploadBook(Uri data) {
        try{
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading");
            progressDialog.show();

            StorageReference storageReference = storage.child("ebooks/" + bookTitle.toLowerCase() + System.currentTimeMillis() + format);
            storageReference.putFile(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                    while ((!uriTask.isComplete())) {
                        Uri url = uriTask.getResult();
                        Book book = new Book(bookTitle, url.toString());

                        Log.d(TAG, "onSuccess: url: " + url);
                    }
                        Toast.makeText(UploadActivity.this, "File uploaded!", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    double progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                    progressDialog.setMessage("Uploaded " + (int) progress + "%");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(UploadActivity.this, "Upload failed!", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onFailure: " + e.getMessage());
                    finish();
                }
            }).addOnCanceledListener(new OnCanceledListener() {
                @Override
                public void onCanceled() {
                    Log.d(TAG, "onCanceled: Operaci[on cancelada!. Volviendo...");
                    finish();
                }
            });


    } catch (Exception e){
            Log.d(TAG, "uploadBook: Choose failed" + e.getMessage());
            finish();
        }
    }

}