package com.example.collegeapp.notice

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.collegeapp.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso

class NoticeAdapter: RecyclerView.Adapter<NoticeAdapter.NoticeViewAdapter> {

    private lateinit var context: Context
    private lateinit var list:ArrayList<NoticeData>

    constructor(context: Context,list: ArrayList<NoticeData>) : super() {
        this.context = context
        this.list = list
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoticeViewAdapter {
        var view:View = LayoutInflater.from(context).inflate(R.layout.newsfeed_item_layout,parent,false)
        return NoticeViewAdapter(view)
    }



    override fun onBindViewHolder(holder: NoticeViewAdapter, position: Int) {

        var currentItem: NoticeData = list.get(position)

        holder.deleteNoticeTitle.setText(currentItem.title)
        try {
            if (currentItem.image!=null) {
                Picasso.get().load(currentItem.image).into(holder.deleteNoticeImage)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        holder.deleteNotice.setOnClickListener(object:View.OnClickListener {

            override fun onClick(view:View){
                var reference:DatabaseReference = FirebaseDatabase.getInstance().getReference().child("Notice")
                reference.child(currentItem.key).removeValue()
                        .addOnCompleteListener(OnCompleteListener {
                            fun onComplete(task: Task<Void>){
                                Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show()

                            }
                        }).addOnFailureListener(OnFailureListener {
                            fun onFailure(e:Exception){
                                Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
                                notifyItemRemoved(position)

                            }
                        })
            }

        })
    }

    override fun getItemCount(): Int {
        return list.size
    }

    public class NoticeViewAdapter:RecyclerView.ViewHolder{
         lateinit var deleteNotice:Button
        lateinit var deleteNoticeTitle: TextView
         lateinit var deleteNoticeImage:ImageView


        constructor(itemView: View) : super(itemView){
            deleteNotice = itemView.findViewById(R.id.deleteNotice)
            deleteNoticeTitle = itemView.findViewById(R.id.deleteNoticeTitle)
            deleteNoticeImage = itemView.findViewById(R.id.deleteNoticeImage)

        }

    }
}