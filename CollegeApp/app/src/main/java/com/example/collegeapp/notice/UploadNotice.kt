package com.example.collegeapp.notice

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.cardview.widget.CardView
import com.example.collegeapp.R
import com.example.collegeapp.faculty.TeacherData
//import com.example.collegeapp.ProgressDialog.Companion.progressDialog
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class UploadNotice : AppCompatActivity() {

    private lateinit var addImage: CardView  //*
    private lateinit var noticeImageView: ImageView  //*
    private lateinit var noticeTitle: EditText  //*
    private lateinit var uploadNoticeBtn: Button  //*
    private lateinit var bitmap: Bitmap  //*
    private lateinit var reference: DatabaseReference //*
    private lateinit var dbRef: DatabaseReference  //*
    private lateinit var storageReference: StorageReference  //*
    private var downloadUrl : String = "" //*
    private lateinit var downloadUri: String  //*
    private var imageUri: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_notice)
        reference = FirebaseDatabase.getInstance().getReference() //*
        dbRef = FirebaseDatabase.getInstance().getReference() //*
        storageReference = FirebaseStorage.getInstance().getReference() //*

        lateinit var uri: Uri

        addImage = findViewById(R.id.addImage)  //*

        addImage.setOnClickListener {
            openGallery()
        } //*

        noticeImageView = findViewById(R.id.noticeImageView)
        noticeTitle = findViewById(R.id.noticeTitle)  //*
        uploadNoticeBtn = findViewById(R.id.uploadNoticeBtn)  //*
        uploadNoticeBtn.setOnClickListener(View.OnClickListener {
            onClick(uploadNoticeBtn)
        })
    }


    private fun uploadData(str:String) {
        val title: String = noticeTitle.text.toString()
        val uniqueKey: String = reference.child("Notice").push().key.toString()
        dbRef = reference.child("Notice").child(uniqueKey)
        val calForData: Calendar = Calendar.getInstance()
        val currentDate: SimpleDateFormat = SimpleDateFormat("dd-MM-yy")
        val date: String = currentDate.format(calForData.time)

        val calForTime: Calendar = Calendar.getInstance()
        val currentTime: SimpleDateFormat = SimpleDateFormat("hh:mm a")
        val time: String = currentTime.format(calForTime.time)
        var noticeData= NoticeData(title,str,date,time,uniqueKey)
//        val map: HashMap<String, String> = HashMap<String, String>()
//        map.put("date", date)
//        map.put("image", str)
//        map.put("key", uniqueKey)
//        map.put("time", time)
//        map.put("title", title)

        dbRef.setValue(noticeData)
            .addOnCompleteListener(OnCompleteListener {
                fun onComplete(task: Task<Void>) {
                    Log.i("bjhebv", "onComplete")
                }
            })
            .addOnFailureListener(OnFailureListener {
                fun onFailure(e: Exception?) {
                    Log.i("bjhebv", "onFailure" + e.toString())
                    Toast.makeText(this@UploadNotice, "Something went wrong", Toast.LENGTH_SHORT)
                        .show()
                }
            })

    }
        private fun openGallery() {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            getResult.launch(gallery)

        }//*


        private val getResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {

                if (it.resultCode == Activity.RESULT_OK) {

                    imageUri = it.data?.data
                    try {
                        imageUri?.let {
                            if(Build.VERSION.SDK_INT < 28) {
                                val bitmap = MediaStore.Images.Media.getBitmap(
                                    this.contentResolver,
                                    imageUri
                                )
                                noticeImageView.setImageBitmap(bitmap)
                            } else {
                                val source = ImageDecoder.createSource(this.contentResolver, imageUri!!)
                                val bitmap = ImageDecoder.decodeBitmap(source)
                                noticeImageView.setImageBitmap(bitmap)
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }//*

        private fun upload() {

            var mReference = imageUri?.lastPathSegment?.let { storageReference.child("Notice").child(it) }
            try {
                if (mReference != null) {
                    imageUri?.let {
                        mReference.putFile(it).addOnSuccessListener { taskSnapshot: UploadTask.TaskSnapshot? -> var url = taskSnapshot?.storage?.downloadUrl.toString()
                            val dwnTxt = findViewById<View>(R.id.dwntxt) as TextView
//                        dwnTxt.text = url.toString()
                            downloadUri=url.toString()
                            var uriTask= taskSnapshot?.storage?.downloadUrl
                            while(!uriTask?.isComplete()!!){}
                            val uri_new =uriTask.result
//                        uploadData(taskSnapshot?.storage?.downloadUrl?.getResult().toString())
                            uploadData(uri_new.toString())
                            Toast.makeText(this, "Successfully Updated :)", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }catch (e: Exception) {
                Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show()
            }


        }
        fun onClick(view: View) {

            if (noticeTitle.getText().toString().isEmpty()) {
                noticeTitle.setError("Empty")
                noticeTitle.requestFocus()
            }

            else {
                upload()
            }
        }


    }