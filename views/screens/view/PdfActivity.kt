package com.domaintest.pdf_viewer_alpha.views.screens.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.domaintest.pdf_viewer_alpha.R
import com.domaintest.pdf_viewer_alpha.databinding.ActivityPdfBinding

class PdfActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPdfBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPdfBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnPDF.setOnClickListener{
            launcher.launch("application/pdf")
        }
    }

    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent()){
        uri -> uri?.let {
            binding.pdfView.fromUri(it).load()
    }
    }
}