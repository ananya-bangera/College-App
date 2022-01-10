package com.example.collegeapp.image

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.collegeapp.R

import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class DisplayImageActivity : AppCompatActivity() {
    private lateinit var deleteImageRecycler: RecyclerView
    private lateinit var disprogressBar: ProgressBar
    private lateinit var list: ArrayList<ImageData>
    private lateinit var list2: ArrayList<String>
    private lateinit var adapter: ImageAdapter

    private lateinit var category:String

    private lateinit var reference: DatabaseReference //*
    private lateinit var reference2: DatabaseReference //*
    private lateinit var reference3: DatabaseReference //*
    private lateinit var dbRef: DatabaseReference  //*
    private lateinit var storageReference: StorageReference  //*
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_image)
//        category= intent.getStringExtra("category").toString()
        dbRef = FirebaseDatabase.getInstance().getReference() //*
        storageReference = FirebaseStorage.getInstance().getReference() //*
        reference = FirebaseDatabase.getInstance().getReference().child("gallery")
        reference2 = FirebaseDatabase.getInstance().getReference().child("gallery")
        reference3 = FirebaseDatabase.getInstance().getReference().child("gallery")
        deleteImageRecycler = findViewById(R.id.deleteImageRecycler)
        disprogressBar = findViewById(R.id.disprogressBar)
        deleteImageRecycler.layoutManager = LinearLayoutManager(this)
        deleteImageRecycler.setHasFixedSize(true)
        getImage()

    }

    private fun getImage() {
        list = ArrayList<ImageData>()

                reference.child("Convocation").addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(datasnapshot: DataSnapshot) {

                        for (snapshot: DataSnapshot in datasnapshot.children) {
                            var data: ImageData? = snapshot.getValue(ImageData::class.java)
                            if (data != null) {
                                category = data.category
                                (list).add(data)
                            }

                        }

                        adapter = ImageAdapter(this@DisplayImageActivity, list)
                        adapter.notifyDataSetChanged()
                        disprogressBar.visibility = View.GONE
                        deleteImageRecycler.adapter = adapter


                    }


                    override fun onCancelled(databaseError: DatabaseError) {
                        disprogressBar.visibility = View.GONE
                        Toast.makeText(
                            this@DisplayImageActivity,
                            databaseError.message,
                            Toast.LENGTH_SHORT
                        ).show()

                    }
                })
        reference2.child("Other Events").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(datasnapshot: DataSnapshot) {

                for(snapshot : DataSnapshot in datasnapshot.children){
                    var data:ImageData? = snapshot.getValue(ImageData::class.java)
                    if (data != null) {
                        (list).add(data)
                    }

                }
                adapter = ImageAdapter(this@DisplayImageActivity,list)
                adapter.notifyDataSetChanged()
                disprogressBar.visibility = View.GONE
                deleteImageRecycler.adapter = adapter
            }

            override fun onCancelled(databaseError: DatabaseError) {
                disprogressBar.visibility = View.GONE
                Toast.makeText(this@DisplayImageActivity,databaseError.message, Toast.LENGTH_SHORT).show()

            }
        })
        reference3.child("Independence Day").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(datasnapshot: DataSnapshot) {

                for(snapshot : DataSnapshot in datasnapshot.children){
                    var data:ImageData? = snapshot.getValue(ImageData::class.java)
                    if (data != null) {
                        (list).add(data)
                    }

                }
                adapter = ImageAdapter(this@DisplayImageActivity,list)
                adapter.notifyDataSetChanged()
                disprogressBar.visibility = View.GONE
                deleteImageRecycler.adapter = adapter
            }

            override fun onCancelled(databaseError: DatabaseError) {
                disprogressBar.visibility = View.GONE
                Toast.makeText(this@DisplayImageActivity,databaseError.message, Toast.LENGTH_SHORT).show()

            }
        })



    }
}

