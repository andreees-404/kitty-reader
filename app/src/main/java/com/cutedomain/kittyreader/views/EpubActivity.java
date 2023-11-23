package com.cutedomain.kittyreader.views;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.cutedomain.kittyreader.R;
import com.cutedomain.kittyreader.domain.utils.FileUtil;
import com.cutedomain.kittyreader.domain.utils.MyOnTouchListener;
import com.cutedomain.kittyreader.domain.utils.OnFilePathRecivedListener;
import com.cutedomain.kittyreader.models.EBookJTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import nl.siegmann.epublib.domain.Author;
import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Metadata;
import nl.siegmann.epublib.domain.Resource;
import nl.siegmann.epublib.domain.TOCReference;
import nl.siegmann.epublib.epub.EpubReader;

public class EpubActivity extends AppCompatActivity {

    private FileUtil fileUtil;

    /* Ebook variables */
    private Metadata metadata;
    private Book book;

    Resource coverPage;


    /*
    * Notificador al recibir la ruta del archivo */
    private OnFilePathRecivedListener pathListener;
    private List<TOCReference> indexReferences;



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
    private Resource coverImage;
    private float INITIAL_ZOOM_SCALE = 1.0f;

    @SuppressLint({"ClickableViewAccessibility", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_epub);

        pageContent = findViewById(R.id.pageContent);
        pageContent.setVerticalScrollBarEnabled(true);
        pageContent.setHorizontalScrollBarEnabled(true);

        pageContent.getSettings().setJavaScriptEnabled(true);
        pageContent.getSettings().setBuiltInZoomControls(true);
        pageContent.getSettings().setDisplayZoomControls(false);

        pageContent.getSettings().setSupportZoom(true);
        pageContent.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
        pageTitle = findViewById(R.id.pageTitle);

        MyOnTouchListener listener = new MyOnTouchListener(this, this, pageContent);
        pageContent.setOnTouchListener(listener);



        //pageContent.setOnTouchListener(new View.OnTouchListener() {
        //    private float startX;

        //    // Distancia mínima a deslizar por la pantalla
        //    public static final float MIN_SWIPE_DISTANCE = 500;



        //    @SuppressLint("ClickableViewAccessibility")
        //    @Override
        //    public boolean onTouch(View v, MotionEvent event) {
        //        switch (event.getAction()){
        //            case MotionEvent.ACTION_DOWN:
        //            startX = event.getX();
        //            break;

        //            case MotionEvent.ACTION_UP:
        //                float endX = event.getX();
        //                float deltaX = endX - startX;

        //                if (Math.abs(deltaX) > MIN_SWIPE_DISTANCE){
        //                    if (deltaX > 0){
        //                        showBackPage();
        //                    } else {
        //                        showNextPage();
        //                    }
        //                }
        //                break;
        //        }
        //        return false;
        //    }
        //});
        pathListener = new OnFilePathRecivedListener() {
            @Override
            public void onFilePathRecivedListener(String path) {
                if (path != null){
                    Log.d(TAG, "onFilePathRecivedListener: successful");

                    // Mostrar el libro
                    openBook(path);

                    // Mostrar el contenido
                    showPage(currentPage);
                } else {
                    Log.d(TAG, "onFilePathRecivedListener: no such file or directory!");
                }
            }};
        setFilePathRecived(pathListener);

        // Copiar el libro desde la carpeta assets de Android Studio

        // Obtener la ruta de archivo
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
    public void showNextPage() {
        if (currentPage < book.getContents().size() -1){
            currentPage++;
            showPage(currentPage);
        }
    }

    /*
     * Mostrar la página anterior a la actual
     *  */
    public void showBackPage() {
        if (currentPage > 0){
            currentPage--;
            showPage(currentPage);
        }

    }

    public void resetZoom(){
        WebSettings settings = pageContent.getSettings();
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);
        pageContent.setInitialScale((int) (INITIAL_ZOOM_SCALE * 100));
        Toast.makeText(pageContent.getContext(), "Zoom reset", Toast.LENGTH_SHORT).show();
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
        String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + APP_DIR + directory;
        File dir = new File(dirPath);
        if (!dir.exists()) {
            // Crear el directorio /kittyreader/ebooks
            dir.mkdirs();
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
     *  El libro debe estar en la carpeta /kittyreader/books
     * */
    private void getFilePath() {
        try {
        launcher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {

            @Override
            public void onActivityResult(Uri o) {
                if (o != null) {
                    Log.d(TAG, "onActivityResult: uri " + o.getAuthority());
                    String path = FileUtil.getRealPathFromURI(getApplicationContext(), o);
                    Log.d(TAG, "onActivityResult: " + path);
                    try {
                        pathListener.onFilePathRecivedListener(path);
                    } catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });

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


            // Obtener los metadatos
            metadata = book.getMetadata();

            List<Author> author;

            author = metadata.getAuthors();

            Log.d(TAG, "openBook: Autor: " + author.get(0).toString());
            // Cargar la imagen de portada
            coverPage = book.getCoverPage();
            coverImage = book.getCoverImage();

            EBookJTest _book = new EBookJTest(author.get(0).toString(), metadata.getTitles().get(0));

            Log.d(TAG, "openBook: autor = " + _book.getAuthor());
            // Cargar las referencias -> Índice
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
        try {
            if (book != null && pageIndex >= 0 && pageIndex < book.getContents().size()) {
                Resource page = book.getContents().get(pageIndex);
                if (page != null && coverImage != null) {
                    setContentView(page, coverPage);

                    // Cargar el título del libro
                    pageTitle.setText(book.getTitle());


                } else {
                    Log.d(TAG, "showPage: Error with page or cover page...");
                }
            }
        }catch (Exception e){
            Log.d(TAG, "showPage: Error!");
            e.printStackTrace();
        }
    }



    /*
     * Cargar el contenido en la página
     *
     * @param page
     *   Página del libro
     */
    private void setContentView(Resource page, Resource coverImage) {
        try {
            Log.d(TAG, "showPage: loading book in: " + currentPage);
            InputStream inputStream = page.getInputStream();
            InputStream imageStream = coverImage.getInputStream();

            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            inputStream.close();

            byte[] imageBuffer = new byte[imageStream.available()];
            imageStream.read(imageBuffer);
            imageStream.close();

            // Cargar el contenido
            String content = new String(buffer, "UTF-8");
            pageContent.setWebViewClient(new WebViewClient());
            pageContent.loadDataWithBaseURL(null, content, "text/html", "UTF-8", null);

        } catch (IOException e) {
            Log.d(TAG, "showPage: An error was ocurred!");
            e.printStackTrace();
        }
    }



    private void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


}