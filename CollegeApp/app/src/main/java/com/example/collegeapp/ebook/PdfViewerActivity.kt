package com.example.collegeapp.ebook

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.collegeapp.R
import com.github.barteksc.pdfviewer.PDFView
import java.io.InputStream

class PdfViewerActivity : AppCompatActivity() {
    private lateinit var url:String
    private lateinit var pdfView: PDFView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdf_viewer)
        url = intent.getStringExtra("pdfUrl").toString()
        pdfView = findViewById(R.id.pdfView)
    }

//    private fun PdfDownload : AsyncTask<String,Void,InputStream>{
//        override fun doInBackground:InputStream(strings){
//
//        }
//    }
}