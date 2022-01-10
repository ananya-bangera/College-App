package com.example.collegeapp.ebook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.collegeapp.R
import com.example.collegeapp.notice.NoticeAdapter
import com.example.collegeapp.notice.NoticeData
import com.google.firebase.database.*

class EbookActivity : AppCompatActivity() {
    private lateinit var ebookRecycler: RecyclerView
    private lateinit var eprogressBar: ProgressBar
    private lateinit var list: ArrayList<EbookData>
    private lateinit var adapter: EbookAdapter

    private lateinit var reference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ebook)
        ebookRecycler = findViewById(R.id.ebookRecycler)
        eprogressBar = findViewById(R.id.eprogressBar)
        reference = FirebaseDatabase.getInstance().getReference().child("pdf")
        ebookRecycler.layoutManager = LinearLayoutManager(this)
        ebookRecycler.setHasFixedSize(true)

        getPdf()

    }

    private fun getPdf() {
        reference.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(datasnapshot: DataSnapshot) {
                list = ArrayList<EbookData>()
                for(snapshot : DataSnapshot in datasnapshot.children){
                    var data: EbookData? = snapshot.getValue(EbookData::class.java)
                    if (data != null) {
                        (list as ArrayList<EbookData>).add(data)
                    }

                }
                adapter = EbookAdapter(this@EbookActivity,list)
                adapter.notifyDataSetChanged()
                eprogressBar.visibility = View.GONE
                ebookRecycler.adapter = adapter
            }

            override fun onCancelled(databaseError: DatabaseError) {
                eprogressBar.visibility = View.GONE
                Toast.makeText(this@EbookActivity,databaseError.message, Toast.LENGTH_SHORT).show()

            }
        })
    }
}