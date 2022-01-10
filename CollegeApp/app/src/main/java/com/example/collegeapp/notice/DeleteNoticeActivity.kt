package com.example.collegeapp.notice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.collegeapp.R
import com.google.firebase.database.*

class DeleteNoticeActivity : AppCompatActivity() {
    private lateinit var deleteNoticeRecycler: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var list: ArrayList<NoticeData>
    private lateinit var adapter: NoticeAdapter

    private lateinit var reference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete_notice)
        deleteNoticeRecycler = findViewById(R.id.deleteNoticeRecycler)
        progressBar = findViewById(R.id.progressBar)
        reference = FirebaseDatabase.getInstance().getReference().child("Notice")
        deleteNoticeRecycler.layoutManager = LinearLayoutManager(this)
        deleteNoticeRecycler.setHasFixedSize(true)
        getNotice()

    }

    private fun getNotice() {
        reference.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(datasnapshot: DataSnapshot) {
                list = ArrayList<NoticeData>()
                for(snapshot : DataSnapshot in datasnapshot.children){
                    var data:NoticeData? = snapshot.getValue(NoticeData::class.java)
                    if (data != null) {
                        (list as ArrayList<NoticeData>).add(data)
                    }

                }
                adapter = NoticeAdapter(this@DeleteNoticeActivity,list)
                adapter.notifyDataSetChanged()
                progressBar.visibility = View.GONE
                deleteNoticeRecycler.adapter = adapter
            }

            override fun onCancelled(databaseError: DatabaseError) {
                progressBar.visibility = View.GONE
                Toast.makeText(this@DeleteNoticeActivity,databaseError.message, Toast.LENGTH_SHORT).show()

            }
        })
    }
}