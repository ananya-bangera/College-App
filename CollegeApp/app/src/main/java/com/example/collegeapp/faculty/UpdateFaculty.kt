package com.example.collegeapp.faculty

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.collegeapp.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*

class UpdateFaculty : AppCompatActivity() {


    private lateinit var fab: FloatingActionButton
    private lateinit var csDepartment: RecyclerView
    private lateinit var itDepartment: RecyclerView
    private lateinit var eeDepartment: RecyclerView
    private lateinit var meDepartment: RecyclerView
    private lateinit var meNoData: LinearLayout
    private lateinit var itNoData: LinearLayout
    private lateinit var eeNoData: LinearLayout
    private lateinit var csNoData: LinearLayout
    private lateinit var list1: ArrayList<TeacherData>
    private lateinit var list2: ArrayList<TeacherData>
    private lateinit var list3: ArrayList<TeacherData>
    private lateinit var list4: ArrayList<TeacherData>
    private lateinit var reference: DatabaseReference
    private lateinit var dbRef: DatabaseReference
    private lateinit var adapter: TeacherAdapter
    private var uri: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_faculty)

        meNoData = findViewById(R.id.meNoData)
        itNoData = findViewById(R.id.itNoData)
        eeNoData = findViewById(R.id.eeNoData)
        csNoData = findViewById(R.id.csNoData)
        csDepartment = findViewById(R.id.csDepartment)
        itDepartment = findViewById(R.id.itDepartment)
        eeDepartment = findViewById(R.id.eeDepartment)
        meDepartment = findViewById(R.id.meDepartment)
//        meNoData = findViewById(R.id.meNoData)
        reference = FirebaseDatabase.getInstance().getReference().child("Teacher")
        csDepartment()
        itDepartment()
        eeDepartment()
        meDepartment()
        fab = findViewById(R.id.fab)

    }
    fun onClick(view:View){
        intent = Intent(this@UpdateFaculty, AddTeachers::class.java)
        startActivity(intent)
    }
    fun csDepartment(){
        dbRef=reference.child("Computer Science")
        dbRef.addValueEventListener(object:ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot){
                list1 =  ArrayList<TeacherData>()
                if(!dataSnapshot.exists()){
                    csNoData.visibility = View.VISIBLE
                    csDepartment.visibility = View.GONE

                }else{
                    csNoData.visibility = View.GONE
                    csDepartment.visibility = View.VISIBLE
//                    var snapshot : DataSnapshot
                    for( snapshot in dataSnapshot.children){
                        var data : TeacherData? = snapshot.getValue(TeacherData::class.java)
                        if (data != null) {
                            (list1).add(data)
                        }
                    }
                    csDepartment.setHasFixedSize(true)
                    csDepartment.layoutManager = LinearLayoutManager(this@UpdateFaculty)
                    adapter = TeacherAdapter(list1,this@UpdateFaculty,"Computer Science")
                    csDepartment.adapter = adapter
                }
            }
            override fun onCancelled(databaseError: DatabaseError){
                Toast.makeText(this@UpdateFaculty,databaseError.getMessage(),Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun itDepartment(){
        dbRef=reference.child("Information Technology")
        dbRef.addValueEventListener(object:ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot){
                list2 =  ArrayList<TeacherData>()
                if(!dataSnapshot.exists()){
                    itNoData.visibility = View.VISIBLE
                    itDepartment.visibility = View.GONE

                }else{
                    itNoData.visibility = View.GONE
                    itDepartment.visibility = View.VISIBLE

                    for( snapshot in dataSnapshot.children){
                        var data : TeacherData? = snapshot.getValue(TeacherData::class.java)
                        if (data != null) {
                            list2.add(data)
                        }else{
//                            lis
//                            Toast.makeText(this@UpdateFaculty,"missing",Toast.LENGTH_SHORT).show()
                        }
                    }
                    itDepartment.setHasFixedSize(true)
                    itDepartment.layoutManager = LinearLayoutManager(this@UpdateFaculty)
                    adapter = TeacherAdapter(list2,this@UpdateFaculty,"Information Technology")
                    itDepartment.adapter = adapter
                }
            }
            override fun onCancelled(databaseError: DatabaseError){
                Toast.makeText(this@UpdateFaculty,databaseError.getMessage(),Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun eeDepartment(){
        dbRef=reference.child("Electrical Engineering")
        dbRef.addValueEventListener(object:ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot){
                list3 =  ArrayList<TeacherData>()
                if(!dataSnapshot.exists()){
                    eeNoData.visibility = View.VISIBLE
                    eeDepartment.visibility = View.GONE

                }else{
                    eeNoData.visibility = View.GONE
                    eeDepartment.visibility = View.VISIBLE
                    for( snapshot in dataSnapshot.children){
                        var data : TeacherData? = snapshot.getValue(TeacherData::class.java)
                        if (data != null) {
                            (list3 as ArrayList<TeacherData>).add(data)
                        }
                    }
                    eeDepartment.setHasFixedSize(true)
                    eeDepartment.layoutManager = LinearLayoutManager(this@UpdateFaculty)
                    adapter = TeacherAdapter(list3,this@UpdateFaculty,"Electrical Engineering")
                    eeDepartment.adapter = adapter
                }
            }
            override fun onCancelled(databaseError: DatabaseError){
                Toast.makeText(this@UpdateFaculty,databaseError.getMessage(),Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun meDepartment(){
        dbRef=reference.child("Mechanical Engineering")
        dbRef.addValueEventListener(object:ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot){
                list4 =  ArrayList<TeacherData>()
                if(!dataSnapshot.exists()){
                    meNoData.visibility = View.VISIBLE
                    meDepartment.visibility = View.GONE

                }else{
                    meNoData.visibility = View.GONE
                    meDepartment.visibility = View.VISIBLE
                    for( snapshot in dataSnapshot.children){
                        var data : TeacherData? = snapshot.getValue(TeacherData::class.java)
                        if (data != null) {
                            (list4 as ArrayList<TeacherData>).add(data)
                        }
                    }
                    for( snapshot in dataSnapshot.children){
                        var data : TeacherData? = snapshot.getValue(TeacherData::class.java)
                        if (data != null) {
                            (list4 as ArrayList<TeacherData>).add(data)
                        }
                    }
                    meDepartment.setHasFixedSize(true)
                    meDepartment.layoutManager = LinearLayoutManager(this@UpdateFaculty)
                    adapter = TeacherAdapter(list4,this@UpdateFaculty,"Mechanical Engineering")
                    meDepartment.adapter = adapter
                }
            }
            override fun onCancelled(databaseError: DatabaseError){
                Toast.makeText(this@UpdateFaculty,databaseError.getMessage(),Toast.LENGTH_SHORT).show()
            }
        })
    }



}

