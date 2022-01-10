package com.example.collegeapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.cardview.widget.CardView
import com.example.collegeapp.ebook.EbookActivity
import com.example.collegeapp.ebook.UploadPdfActivity
import com.example.collegeapp.faculty.UpdateFaculty
import com.example.collegeapp.image.DisplayImageActivity
import com.example.collegeapp.image.UploadImage
import com.example.collegeapp.notice.DeleteNoticeActivity
import com.example.collegeapp.notice.UploadNotice

class MainActivity : AppCompatActivity(){
    lateinit var uploadNotice: CardView
    lateinit var addGalleryImage: CardView
    lateinit var addPdf: CardView
    lateinit var faculty: CardView
    lateinit var deleteNotice: CardView
    lateinit var displayImage: CardView
    lateinit var uploadPdf: CardView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        uploadNotice = findViewById(R.id.addNotice)
        addGalleryImage = findViewById(R.id.addGalleryImage)
        addPdf = findViewById(R.id.addPdf)
        faculty = findViewById(R.id.faculty)
        deleteNotice = findViewById(R.id.deleteNotice)
        displayImage = findViewById(R.id.deleteNotice)
        uploadPdf = findViewById(R.id.deleteNotice)
        uploadNotice.setOnClickListener(this)
        addGalleryImage.setOnClickListener(this)
        addPdf.setOnClickListener(this)
        faculty.setOnClickListener(this)
        deleteNotice.setOnClickListener(this)
        displayImage.setOnClickListener(this)
        uploadPdf.setOnClickListener(this)
    }
    fun onClick(view: View){
      if (view.getId()==R.id.addNotice)
        {
             intent = Intent(this, UploadNotice::class.java)

            startActivity(intent)
        }
        else if (view.getId()==R.id.addGalleryImage)
        {

             intent = Intent(this, UploadImage::class.java)
            startActivity(intent)
        }
      else if (view.getId()==R.id.addPdf)
        {
             intent = Intent(this, UploadPdfActivity::class.java)
            startActivity(intent)
        }
      else if (view.getId()==R.id.faculty)
        {
             intent = Intent(this, UpdateFaculty::class.java)
            startActivity(intent)
        }
      else if (view.getId()==R.id.deleteNotice)
        {
             intent = Intent(this, DeleteNoticeActivity::class.java)
            startActivity(intent)
        }
 else if (view.getId()==R.id.displayImage)
        {
             intent = Intent(this, DisplayImageActivity::class.java)
            startActivity(intent)
        }
 else if (view.getId()==R.id.uploadPdf)
        {
             intent = Intent(this, EbookActivity::class.java)
            startActivity(intent)
        }

    }
}

private fun CardView.setOnClickListener(mainActivity: MainActivity) {

}
