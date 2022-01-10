package com.example.collegeapp.faculty

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
import com.example.collegeapp.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import java.lang.Exception
import java.util.*

class AddTeachers : AppCompatActivity() {
    private lateinit var addTeacherImage:ImageView
    private lateinit var addTeacherName:EditText
    private lateinit var addTeacherEmail:EditText
    private lateinit var addTeacherPost:EditText
    private lateinit var addTeacherCategory:Spinner
    private lateinit var addTeacherBtn:Button
    private lateinit var category: String
    private var imageUri: Uri? = null
    private lateinit var bitmap: Bitmap
    private lateinit var reference: DatabaseReference //*
    private lateinit var dbRef: DatabaseReference  //*
    private lateinit var storageReference: StorageReference  //*
    private lateinit var downloadUri: String  //*
    private lateinit var name: String
    private lateinit var email: String
    private lateinit var post: String
    private var downloadUrl:String= ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_teachers)
        addTeacherImage = findViewById(R.id.addTeacherImage)
        addTeacherName = findViewById(R.id.addTeacherName)
        addTeacherEmail = findViewById(R.id.addTeacherEmail)
        addTeacherPost = findViewById(R.id.addTeacherPost)
        addTeacherCategory = findViewById(R.id.addTeacherCategory)
        addTeacherBtn = findViewById(R.id.addTeacherBtn)
        dbRef = FirebaseDatabase.getInstance().getReference() //*
        storageReference = FirebaseStorage.getInstance().getReference() //*
        reference = FirebaseDatabase.getInstance().getReference()
        addTeacherImage.setOnClickListener(View.OnClickListener {
            openGallery()
        })

        var items = arrayOf("Select Category", "Computer Science","Information Technology","Mechanical Engineering","Electrical Engineering")
        addTeacherCategory.adapter = ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,items)
        addTeacherCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                category = addTeacherCategory.selectedItem.toString()
            }
         }
        addTeacherBtn.setOnClickListener(View.OnClickListener {
             onClick(addTeacherBtn)
        })
}

    fun onClick(view:View){
        checkValidation()
    }


    private fun openGallery() {
        val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        getResult.launch(gallery)
    }

    private val getResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {

            if (it.resultCode == Activity.RESULT_OK)
            {

                imageUri = it.data?.data
                try {
                    imageUri?.let {
                        if(Build.VERSION.SDK_INT < 28) {
                            val bitmap = MediaStore.Images.Media.getBitmap(
                                this.contentResolver,
                                imageUri
                            )
                            addTeacherImage.setImageBitmap(bitmap)
                        } else {
                            val source = ImageDecoder.createSource(this.contentResolver, imageUri!!)
                            val bitmap = ImageDecoder.decodeBitmap(source)
                            addTeacherImage.setImageBitmap(bitmap)
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }


    private fun checkValidation() {
        name=addTeacherName.text.toString()
        email = addTeacherEmail.text.toString()
        post = addTeacherPost.text.toString()
        if(name.isEmpty()){
            addTeacherName.setError("Empty")
            addTeacherName.requestFocus()
        }
        else if(email.isEmpty()){
            addTeacherEmail.setError("Empty")
            addTeacherEmail.requestFocus()
        }
        else if(post.isEmpty()){
            addTeacherPost.setError("Empty")
            addTeacherPost.requestFocus()
        }
        else if(category.equals("Select Category")){
            Toast.makeText(this@AddTeachers,"Please provide teacher category",Toast.LENGTH_SHORT).show()
        }
        else{
            upload()
        }
    }

    private fun upload() {

        var mReference = imageUri?.lastPathSegment?.let { storageReference.child("Teacher").child(it) }
        try {

            if (mReference != null) {
                imageUri?.let {
                    mReference.putFile(it).addOnSuccessListener { taskSnapshot: UploadTask.TaskSnapshot? -> var url = taskSnapshot?.storage?.downloadUrl.toString()
                        downloadUri=url.toString()
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

    private fun uploadData(str:String) {
        val uniqueKey : String = reference.child("Teacher").child(category).push().key.toString()
        dbRef=reference.child("Teacher").child(category).child(uniqueKey)

        var teacherData= TeacherData(name,email,post,str,uniqueKey)
//        val map:HashMap<String,String> = HashMap<String,String>()
//        map.put("name", name)
//        map.put("email", email)
//        map.put("image", str)
//        map.put("key", uniqueKey)
//        map.put("post", post)


        dbRef.setValue(teacherData)
                .addOnCompleteListener(OnCompleteListener {
                    fun onComplete(task:Task<Void>)
                    { Log.i("bjhebv", "onComplete")
                        Toast.makeText(this@AddTeachers,"Teacher added",Toast.LENGTH_SHORT).show()
                    }
                })
                .addOnFailureListener(OnFailureListener {
                    fun onFailure(e:Exception?){
                        Log.i("bjhebv", "onFailure"+e.toString())
                        Toast.makeText(this@AddTeachers,"Something went wrong",Toast.LENGTH_SHORT).show()
                    }
                })
    }//*
}
