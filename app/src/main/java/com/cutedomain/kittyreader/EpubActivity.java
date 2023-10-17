package com.cutedomain.kittyreader;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.documentfile.provider.DocumentFile;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.cutedomain.kittyreader.domain.OnFilePathRecivedListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Stack;

import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Resource;
import nl.siegmann.epublib.domain.TOCReference;
import nl.siegmann.epublib.epub.EpubReader;

public class EpubActivity extends AppCompatActivity {

    private static final String TAG = "EpubActivity";
    private static final String APP_DIR = "/kittyreader";
    private ActivityResultLauncher<String> launcher;

    // Ruta de los libros
    private static final String BOOKS_DIR = "/ebooks";
    private String bookPath = null;

    // Contenido del libro
    private WebView pageContent;


    // Título del libro
    private TextView pageTitle;
    private int currentPage = 1;
    private Book book;

    // Notificador de cuando se recibe la ruta del libro
    private OnFilePathRecivedListener pathListener;

    // Lista de los índices del libro
    private List<TOCReference> indexReferences;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_epub);

        pageContent = findViewById(R.id.pageContent);
        pageContent.setVerticalScrollBarEnabled(true);
        pageContent.setHorizontalScrollBarEnabled(true);
        pageTitle = findViewById(R.id.pageTitle);

        pageContent.setOnTouchListener(new View.OnTouchListener() {
            private float startX;

            // Distancia mínima a deslizar por la pantalla
            public static final float MIN_SWIPE_DISTANCE = 500;

            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                    startX = event.getX();
                    break;

                    case MotionEvent.ACTION_UP:
                        float endX = event.getX();
                        float deltaX = endX - startX;

                        if (Math.abs(deltaX) > MIN_SWIPE_DISTANCE){
                            if (deltaX > 0){
                                showBackPage();
                            } else {
                                showNextPage();
                            }
                        }
                        break;
                }
                return false;
            }
        });
        pathListener = new OnFilePathRecivedListener() {
            @Override
            public void onFilePathRecivedListener(String path) {
                if (path != null){
                    Log.d(TAG, "onFilePathRecivedListener: successful");
                    openBook(path);
                    showPage(currentPage);
                } else {
                    Log.d(TAG, "onFilePathRecivedListener: no such file or directory!");
                }
            }};
        setFilePathRecived(pathListener);
        copyFromAssets("La_Mitologia_contada_a_los_ninos-Fernan_Caballero.epub", APP_DIR);
        getFilePath();
    }

    /*
     * Notificar que nuestra ruta ha sido recibida
     * */
    public void setFilePathRecived(OnFilePathRecivedListener listener){
        this.pathListener = listener;
    }

    /*
     * Mostrar la página siguiente a la actual
     * */
    private void showNextPage() {
        if (currentPage < book.getContents().size() -1){
            currentPage++;
            showPage(currentPage);
        }
    }

    /*
     * Mostrar la página anterior a la actual
     * revisando el historial
     *  */
    private void showBackPage() {
        if (currentPage > 0){
            currentPage--;
            showPage(currentPage);
        }

    }


    /* Test function to copy into /ebooks from assets -> not final
     * @param filename
     *   El nombre del libro
     * @param directory
     *   La carpeta donde se guardan los libros
     *
     * Usar una vez para hacer pruebas
     * */
    private void copyFromAssets(String filename, String directory) {
        String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + directory;
        File dir = new File(dirPath);
        if (!dir.exists()) {
            // Crear el directorio /kittyreader/ebooks
            dir.mkdir();
        }
        AssetManager assetManager = getAssets();
        InputStream inputStream;
        OutputStream outputStream;
        try {
            inputStream = assetManager.open(filename);
            File outFile = new File(dirPath, filename);
            outputStream = new FileOutputStream(outFile);
            copyFile(inputStream, outputStream);
            Log.d(TAG, "copyAssets: File saved");
            toast("File saved!");
        } catch (IOException e) {
            Log.d(TAG, "copyAssets: File was not saved " + e);
            toast("File was not saved!");
        }
    }

    private void copyFile(@NonNull InputStream inputStream, OutputStream outputStream) {
        byte[] buffer = new byte[1024];
        int read;
        try {
            while((read = inputStream.read(buffer)) != -1){
                outputStream.write(buffer, 0, read);
            }
            Log.d(TAG, "copyFile: successful");
        } catch (IOException e) {
            Log.d(TAG, "copyFile: " + e.getMessage());
        }

    }

    /*
     * Obtener la ruta de un archivo que seleccionemos
     * */
    private void getFilePath() {
        launcher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {

            @Override
            public void onActivityResult(Uri o) {
                if (o != null) {
                    System.out.println();
                    DocumentFile documentFile = DocumentFile.fromSingleUri(EpubActivity.this, o);
                    String filename = getNameFromUri(o);
                    if (filename != null) {
                        System.out.println("Nombre del archivo -> " + filename);
                        bookPath = Environment.getExternalStorageDirectory().getAbsolutePath() + BOOKS_DIR + "/" + filename;
                        System.out.println("Path ->" + bookPath);
                        Log.d(TAG, "onActivityResult: File -> " + bookPath);
                        if (pathListener != null) {
                            pathListener.onFilePathRecivedListener(bookPath);
                        }
                    }
                }
            }
        });
        try {
            launcher.launch("*/*");
        } catch (Exception e) {
            Log.d(TAG, "getFilePath: Error!");
            e.printStackTrace();
        }
    }


    /*
     * Epublib usage, abre un libro desde una ruta
     * y crea una instancia de Book para ser usado posteriormente
     *
     * @param filePath
     *   Ruta del archivo
     *
     * Por ahora se admiten sólo los archivos de la carpeta ebooks
     * */
    private void openBook(String filePath) {
        try {
            FileInputStream epubFile = new FileInputStream(filePath);
            book = (new EpubReader()).readEpub(epubFile);

            // Load index
            indexReferences = book.getTableOfContents().getTocReferences();
        } catch (IOException e) {
            Log.d(TAG, "openBook: " + e.getMessage());
        }
    }


    /*
     * Mostrar la página actual
     *
     * @param pageIndex
     *   Posición de la página
     *
     */
    private void showPage(int pageIndex) {
        if (book != null && pageIndex >= 0 && pageIndex < book.getContents().size()) {
            Resource page = book.getContents().get(pageIndex);
            setContentView(page);

        }
    }



    /*
     * Cargar el contenido en la página
     *
     * @param page
     *   Página del libro
     */
    private void setContentView(Resource page) {
        try {
            Log.d(TAG, "showPage: loading book");
            InputStream inputStream = page.getInputStream();
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            inputStream.close();

            // Cargar el contenido
            String content = new String(buffer, "UTF-8");
            pageContent.loadDataWithBaseURL(null, content, "text/html", "UTF-8", null);

        } catch (IOException e) {
            Log.d(TAG, "showPage: An error was ocurred!");
            e.printStackTrace();
        }
    }



    /*
     * Obtener el nombre del arrchivo desde una uri
     *
     * @param uri
     *   Identifica el recurso
     */
    private String getNameFromUri(Uri uri) {
        String filename = null;
        ContentResolver contentResolver = getContentResolver();

        Cursor cursor = contentResolver.query(uri, null, null, null, null);
        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    int displayNameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    // Nombre del archivo -> DISPLAY_NAME
                    String displayName = cursor.getString(displayNameIndex);

                    if (displayName != null) {
                        filename = displayName;
                    } else {
                        String lastPathSegment = uri.getLastPathSegment();
                        if (lastPathSegment != null) {
                            // Si DISPLAY_NAME no está disponible, intentamos obtenerlo
                            // a partir del último segmento de la uri
                            filename = new File(lastPathSegment).getName();
                        }
                    }
                }
            } finally {
                cursor.close();
            }
        }
        return filename;
    }


    private void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


}