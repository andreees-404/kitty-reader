package com.cutedomain.kittyreader.domain.controllers

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.cutedomain.kittyreader.views.EpubActivity
import com.cutedomain.kittyreader.views.PdfActivity

class StoragePermissionsActivity : AppCompatActivity() {

    private var type: String = ""
    private val STORAGE_PERMISSIONS_CODE = 100
    private val TAG = "PermissionsManager"

    // Solo los permisos para leer archivos
    private val storagePermissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        listOf<String>(
            Manifest.permission.MANAGE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET)
    } else {
        listOf<String>(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = intent
        val bundle = intent.extras
        if (bundle != null) {
            type = bundle.getString("type").toString()
            Log.d(TAG, "onCreate: type: $type")
        }


        checkPermissions()
    }


    /* Validar si los permisos han sido aceptados
    * */
    internal fun checkPermissions(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            // El permiso no ha sido aceptado
            requestStoragePermissions()
        } else {
            openFilesManager(type)
        }
    }

    private fun openFilesManager(type: String) {
        if(type == "epub") {
            Intent(this, EpubActivity::class.java).also {
                startActivity(it)
            }
        } else if (type == "pdf"){
            Intent(this, PdfActivity::class.java).also {
                startActivity(it)
            }
        }

        finish()
    }


    /* Solicitar los permisos al usuario
    * */
    private fun requestStoragePermissions() {
        for (i in storagePermissions) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, i)){
                // Todavia no ha aceptado el permiso
                toast("Permisos rechazados")
                finish()
            }else{
            // Todavía no los ha aceptado,
            // así que los pedimos
            ActivityCompat.requestPermissions(this,
                arrayOf(i), STORAGE_PERMISSIONS_CODE)
            }
        }
    }


    /* Se ejecuta esta función cuando finaliza el request de permisos
    * */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == STORAGE_PERMISSIONS_CODE){
            if (grantResults.isNotEmpty()){
                // Algún permiso fue aceptado
                for(i in grantResults){
                    // Iteramos la lista de permisos aceptados
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED){
                        Log.d(TAG, "onRequestPermissionsResult: ${i}: ")
                        openFilesManager(type)
                        finish()
                    }
                }
            }
            else {
                // No se ha aceptado ningún permiso
                Log.d(TAG, "onRequestPermissionsResult: No se ha aceptado ningún permiso")
                toast("Storage permissions required!")
            }
        }
    }

    /* Hacer un toast común
    *
    * @param message Mensaje para el Toast
    * */
    private fun toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}
