package com.example.collegeapp.image

import android.app.Activity
import android.content.Intent
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.collegeapp.R
import com.example.collegeapp.notice.NoticeData
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask


class UploadImage : AppCompatActivity() {

    private lateinit var imageCategory: Spinner  //*
    private lateinit var selectImage: CardView  //*
    private lateinit var uploadImage: Button //*
    private lateinit var galleryImageView: ImageView  //*
    private lateinit var category: String
    private var imageUri: Uri? = null
    private lateinit var dwnTxt : TextView
    private lateinit var reference: DatabaseReference //*
    private lateinit var dbRef: DatabaseReference  //*
    private lateinit var storageReference: StorageReference  //*
    private lateinit var downloadUrl: String  //*

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_image)
//        Toast.makeText(this, "first :)", Toast.LENGTH_LONG).show()
        selectImage = findViewById(R.id.addGalleryImage) //*
        imageCategory = findViewById(R.id.image_category) //*

        uploadImage = findViewById(R.id.uploadImageBtn) //*
        galleryImageView = findViewById(R.id.galleryImageView) //*

        var items = arrayOf("Select Category", "Convocation","Independence Day","Other Events")//*
        imageCategory.adapter = ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,items)//*
        val uploadImageBtn = findViewById<View>(R.id.uploadImageBtn) as Button
        dwnTxt = findViewById(R.id.dwntxt)
        reference = FirebaseDatabase.getInstance().getReference().child("gallery") //*

        storageReference = FirebaseStorage.getInstance().getReference() //*

        imageCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                category = imageCategory.selectedItem.toString()
            }

        }//*

        selectImage.setOnClickListener(View.OnClickListener {

            openGallery()
        })

        uploadImage.setOnClickListener(View.OnClickListener {
            onClick(uploadImage)
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
                                galleryImageView.setImageBitmap(bitmap)
                            } else {
                                val source = ImageDecoder.createSource(this.contentResolver, imageUri!!)
                                val bitmap = ImageDecoder.decodeBitmap(source)
                                galleryImageView.setImageBitmap(bitmap)
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }//*

    private fun upload() {

        var mReference = imageUri?.lastPathSegment?.let { storageReference.child("gallery").child(it) }
        try {
            if (mReference != null) {
                imageUri?.let {
                    mReference.putFile(it).addOnSuccessListener { taskSnapshot: UploadTask.TaskSnapshot? -> var url = taskSnapshot?.storage?.downloadUrl.toString()
                        val dwnTxt = findViewById<View>(R.id.dwntxt) as TextView
//                        dwnTxt.text = url.toString()
//                        downloadUri=url.toString()
                        var uriTask= taskSnapshot?.storage?.downloadUrl
                        while(!uriTask?.isComplete()!!){}
                        val uri_new =uriTask.result
                        uploadData(uri_new.toString())
                        Toast.makeText(this, "Successfully Uploaded :)", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }catch (e: Exception) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show()
        }


    }

    private fun uploadData(str:String)  {
        val uniqueKey : String = reference.child(category).push().key.toString()
        var imageData= ImageData(str,uniqueKey,category)
        reference.child(category).child(uniqueKey)
        .setValue(imageData)
                .addOnCompleteListener(OnCompleteListener {
                    fun onComplete(task: Task<Void>)
                    { Log.i("bjhebv", "onComplete") }
                })
                .addOnFailureListener(OnFailureListener {
                    fun onFailure(e:Exception?){
                        Log.i("bjhebv", "onFailure"+e.toString())
                        Toast.makeText(this@UploadImage,"Something went wrong",Toast.LENGTH_SHORT).show()
                    }
                })
    }

    fun onClick(view:View){
        if(galleryImageView==null){
            Toast.makeText(this, "Please Upload Image", Toast.LENGTH_LONG).show()
        }
        else if (category.equals("Select Category")){
            Toast.makeText(this, "Please Select Image Category", Toast.LENGTH_LONG).show()
        }
        else{
            upload()
        }
    }

}