package com.example.collegeapp.ebook

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.cardview.widget.CardView
import com.example.collegeapp.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import java.io.File
import java.lang.Exception
import java.util.*

class UploadPdfActivity : AppCompatActivity() {
    private lateinit var addPdf: CardView //*
//    lateinit var button: Button
    private lateinit var pdfTitle: EditText  //*
    private lateinit var uploadPdfBtn: Button  //*
    private lateinit var pdfName:String
    private lateinit var pdfData: Uri  //*
    private lateinit var reference: DatabaseReference //*
    private lateinit var dbRef: DatabaseReference  //*
    private lateinit var storageReference: StorageReference  //*
    private var downloadUrl : String = ""  //*
    private lateinit var downloadUri: String  //*
    private var title : String = ""  //*
//    lateinit var mStorage : StorageReference
    lateinit var  pdfTextView : TextView //*
    //    var dialog = ProgressDialog.progressDialog(this)
//    private var uri: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_pdf)
        reference = FirebaseDatabase.getInstance().getReference() //*
        dbRef = FirebaseDatabase.getInstance().getReference() //*
        storageReference = FirebaseStorage.getInstance().getReference() //*
//        lateinit var uri : Uri

        addPdf = findViewById(R.id.addPdf)//*
//        button = findViewById(R.id.uploadPdfBtn)
        pdfTitle = findViewById(R.id.pdfTitle) //*
        uploadPdfBtn = findViewById(R.id.uploadPdfBtn) //*
        pdfTextView = findViewById(R.id.pdfTextView) //*
        val uploadPdfBtn = findViewById<View>(R.id.uploadPdfBtn) as Button
      uploadPdfBtn.setOnClickListener(View.OnClickListener {

            onClickPdf(uploadPdfBtn)
        })
    addPdf.setOnClickListener {
            openGallery()
        }
    }

    private fun openGallery() {
        val gallery = Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        getResult.launch(gallery)
    }

    @SuppressLint("Range")
    private val getResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {

            if (it.resultCode == Activity.RESULT_OK) {
                pdfData = it.data?.data!!
//                Toast.makeText(this, ""+uri, Toast.LENGTH_LONG).show()
                if (pdfData.toString().startsWith("content://")){
                    var cursor:Cursor
                    try {
                        cursor = this@UploadPdfActivity.contentResolver.query(pdfData,null,null,null,null)!!
                        if(cursor!=null && cursor.moveToFirst()){
                            pdfName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))

                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                else if(pdfData.toString().startsWith("file://")){
                    pdfName= File(pdfData.toString()).name
                }
                pdfTextView.text = pdfName
//                upload()
            }
        }

    private fun upload() {
//        var ref = storageReference
        var mReference = pdfData?.lastPathSegment?.let { storageReference.child("pdf/"+"-"+System.currentTimeMillis()+".pdf")}

        try {
            if (mReference != null) {
                pdfData?.let {
                    mReference.putFile(it).addOnSuccessListener { taskSnapshot: UploadTask.TaskSnapshot? -> var url = taskSnapshot?.storage?.downloadUrl.toString()
//                        while(!url.isComplete())
//                        downloadUri=url.toString()
                        var uriTask= taskSnapshot?.storage?.downloadUrl
                        while(!uriTask?.isComplete()!!){}
                        val uri_new =uriTask.result
//                        uploadData(taskSnapshot?.storage?.downloadUrl?.getResult().toString())
                        uploadData(uri_new.toString())
                        val pdfTextView = findViewById<View>(R.id.pdfTextView) as TextView
                        pdfTextView.text = ""
                    }
                }
            }
        }catch (e: Exception) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show()
        }

    }

    private fun uploadData(str:String) {
//        val title :String = noticeTitle.text.toString()
        val uniqueKey : String = reference.child("pdf").push().key.toString()

            dbRef = reference.child("pdf").child(uniqueKey)
//        val map2:HashMap<String,String> = HashMap<String,String>()
//
//        map2.put("pdfTitle", pdfTitle.toString())
//        map2.put("pdfUrl", str)
        var ebookData= EbookData(title,str)
        dbRef.setValue(ebookData)
                .addOnCompleteListener(OnCompleteListener {
                    fun onComplete(task: Task<Void>)
                    { Log.i("bjhebv", "onComplete")
//                        Toast.makeText(this@UploadPdfActivity,"Pdf Uploaded successfully",Toast.LENGTH_SHORT).show()
                    }
                })
                .addOnFailureListener(OnFailureListener {
                    fun onFailure(e:Exception?){
                        Log.i("bjhebv", "onFailure"+e.toString())
                        Toast.makeText(this@UploadPdfActivity,"Something went wrong",Toast.LENGTH_SHORT).show()
                    }
                })
    }

    fun onClickPdf(view:View){

        title = pdfTitle.text.toString()


        if (title.isEmpty()){
            Toast.makeText(this,"Please Enter PDF Title" , Toast.LENGTH_LONG).show()
            pdfTitle.setError("Empty")
            pdfTitle.requestFocus()
        }
        else if(pdfTextView.text.equals("No File Selected")){
                    Toast.makeText(this, "Please Upload Notes", Toast.LENGTH_LONG).show()

        }
        else{
            upload()
        Toast.makeText(this, "Successfully Uploaded :)", Toast.LENGTH_LONG).show()
            pdfTextView.text="No File Selected"

        }


    }
}