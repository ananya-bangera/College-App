package com.example.collegeapp.faculty

import android.app.Activity
import android.content.Intent
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.collegeapp.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.squareup.picasso.Picasso
import java.util.*
import kotlin.collections.HashMap

class UpdateTeacherActivity : AppCompatActivity() {
    private lateinit var updateTeacherImage:ImageView
    private lateinit var updateTeacherName:EditText
    private lateinit var updateTeacherEmail:EditText
    private lateinit var updateTeacherPost:EditText
    private lateinit var updateTeacherBtn:Button
    private lateinit var deleteTeacherBtn:Button
    private var imageUri: Uri? = null
    private lateinit var name:String
    private lateinit var image:String
    private lateinit var email:String
    private lateinit var post:String
    private lateinit var uniqueKey:String
    private lateinit var category:String
    private lateinit var downloadUrl:String

    private lateinit var reference: DatabaseReference //*
    private lateinit var dbRef: DatabaseReference  //*
    private lateinit var storageReference: StorageReference  //*

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_teacher)

        name= intent.getStringExtra("name").toString()
        image= intent.getStringExtra("image").toString()
        email= intent.getStringExtra("email").toString()
        post= intent.getStringExtra("post").toString()
        uniqueKey = intent.getStringExtra("key").toString()
        category= intent.getStringExtra("category").toString()
        updateTeacherImage = findViewById(R.id.updateTeacherImage)
        updateTeacherName = findViewById(R.id.updateTeacherName)
        updateTeacherEmail = findViewById(R.id.updateTeacherEmail)
        updateTeacherPost = findViewById(R.id.updateTeacherPost)
        updateTeacherBtn = findViewById(R.id.updateTeacherBtn)
        deleteTeacherBtn = findViewById(R.id.deleteTeacherBtn)

        dbRef = FirebaseDatabase.getInstance().getReference() //*
        storageReference = FirebaseStorage.getInstance().getReference() //*
        reference = FirebaseDatabase.getInstance().getReference().child("Teacher")

        try {
            Picasso.get().load(image).into(updateTeacherImage)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        updateTeacherEmail.setText(email)
        updateTeacherName.setText(name)
        updateTeacherPost.setText(post)

        updateTeacherImage.setOnClickListener(object:View.OnClickListener {
            override fun onClick(view: View) {
                openGallery()
            }
        })

        updateTeacherBtn.setOnClickListener(object: View.OnClickListener{

            override fun onClick(view: View) {
                name= updateTeacherName.text.toString()
                email = updateTeacherEmail.text.toString()
                post = updateTeacherPost.text.toString()


                checkValidation()
            }
        })
        deleteTeacherBtn.setOnClickListener(object : View.OnClickListener{
            override fun onClick(view: View) {
                deleteData()
            }

        })

    }

    private fun deleteData(){
        reference.child(category).child(uniqueKey).removeValue()
            .addOnCompleteListener(OnCompleteListener {
                fun onComplete(task: Task<Void>)
                { Log.i("bjhebv", "onComplete")
                    Toast.makeText(this@UpdateTeacherActivity,"Teacher deleted successfully :)",Toast.LENGTH_SHORT).show()
                    var intent = Intent(this@UpdateTeacherActivity,UpdateFaculty::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intent)
                }
            }).addOnFailureListener(OnFailureListener {
                fun onFailure(e: java.lang.Exception?){
                    Log.i("bjhebv", "onFailure"+e.toString())
                    Toast.makeText(this@UpdateTeacherActivity,"Something went wrong",Toast.LENGTH_SHORT).show()
                }
            })
    }
  private fun updateData( s:String){
      val hp: HashMap<String, Any> = HashMap<String, Any>()
      hp.put("name",name)
      hp.put("email",email)
      hp.put("post",post)
      hp.put("image",s)

    reference.child(category).child(uniqueKey).updateChildren(hp).addOnSuccessListener( OnSuccessListener(){
       fun onSuccess(o:Objects){
            Toast.makeText(this@UpdateTeacherActivity,"Teacher info updated successfully :)",Toast.LENGTH_SHORT).show()
            var intent = Intent(this@UpdateTeacherActivity,UpdateFaculty::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
    }).addOnFailureListener(object:OnFailureListener {
        override fun onFailure(e:Exception) {
            Toast.makeText(this@UpdateTeacherActivity,"Something went wrong",Toast.LENGTH_SHORT).show()
        }
    })

    }

    private fun checkValidation() {

        if(name.isEmpty()){
            updateTeacherName.setError("Empty")
            updateTeacherName.requestFocus()
        }
        else if(email.isEmpty()){
            updateTeacherEmail.setError("Empty")
            updateTeacherEmail.requestFocus()
        }
        else if(post.isEmpty()){
            updateTeacherPost.setError("Empty")
            updateTeacherPost.requestFocus()
        }
        else if(category.equals("Select Category")){
            Toast.makeText(this@UpdateTeacherActivity,"Please provide teacher category", Toast.LENGTH_SHORT).show()
        }
        else{
            updateData(image)
            upload()
        }
    }
    private fun upload() {

        var mReference = imageUri?.lastPathSegment?.let { storageReference.child("Teacher").child(it) }
        try {

            if (mReference != null) {
                imageUri?.let {
                    mReference.putFile(it).addOnSuccessListener { taskSnapshot: UploadTask.TaskSnapshot? -> var url = taskSnapshot?.storage?.downloadUrl.toString()
                        var uriTask= taskSnapshot?.storage?.downloadUrl
                        while(!uriTask?.isComplete()!!){}
                        val uri_new =uriTask.result
                        updateData(uri_new.toString())
                    }
                }
            }
        }catch (e: java.lang.Exception) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show()
        }
        Toast.makeText(this, "Teacher info updated successfully :)", Toast.LENGTH_LONG).show()

    }

    private fun uploadData(str:String) {
        val uniqueKey : String = reference.child("Teacher").child("category").push().key.toString()
        dbRef=reference.child("Teacher").child("category").child(uniqueKey)

        var teacherData= TeacherData(name,email,post,str,uniqueKey)
        dbRef.setValue(teacherData)
            .addOnCompleteListener(OnCompleteListener {
                fun onComplete(task: Task<Void>)
                { Log.i("bjhebv", "onComplete")
                    Toast.makeText(this@UpdateTeacherActivity,"Teacher added",Toast.LENGTH_SHORT).show()
                }
            })
            .addOnFailureListener(OnFailureListener {
                fun onFailure(e: java.lang.Exception?){
                    Log.i("bjhebv", "onFailure"+e.toString())
                    Toast.makeText(this@UpdateTeacherActivity,"Something went wrong",Toast.LENGTH_SHORT).show()
                }
            })
    }//*

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
                            updateTeacherImage.setImageBitmap(bitmap)
                        } else {
                            val source = ImageDecoder.createSource(this.contentResolver, imageUri!!)
                            val bitmap = ImageDecoder.decodeBitmap(source)
                            updateTeacherImage.setImageBitmap(bitmap)
                        }
                    }
                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                }
            }
        }
}