package com.example.collegeapp.image


import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.collegeapp.R
import com.example.collegeapp.faculty.UpdateTeacherActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso

class ImageAdapter: RecyclerView.Adapter<ImageAdapter.ImageViewAdapter> {

    private lateinit var context: Context
    private lateinit var list:ArrayList<ImageData>

    constructor(context: Context,list: ArrayList<ImageData>) : super() {
        this.context = context
        this.list = list
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewAdapter {
        var view:View = LayoutInflater.from(context).inflate(R.layout.imagesfeed_item_layout,parent,false)
        return ImageViewAdapter(view)
    }



    override fun onBindViewHolder(holder: ImageViewAdapter, position: Int) {

        var currentItem: ImageData = list.get(position)

        holder.displayImageCategory.setText(currentItem.category)
        try {
            if (currentItem.image!=null) {
                Picasso.get().load(currentItem.image).into(holder.displayImage)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        holder.displaydeleteImage.setOnClickListener(object:View.OnClickListener {

            override fun onClick(view:View){
                var reference:DatabaseReference = FirebaseDatabase.getInstance().getReference().child("gallery")
                reference.child(currentItem.category).child(currentItem.key).removeValue()
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

    public class ImageViewAdapter:RecyclerView.ViewHolder{
        lateinit var displaydeleteImage:Button
        lateinit var displayImageCategory: TextView
        lateinit var displayImage:ImageView


        constructor(itemView: View) : super(itemView){
            displaydeleteImage = itemView.findViewById(R.id.displaydeleteImage)
            displayImageCategory = itemView.findViewById(R.id.displayImageCategory)
            displayImage = itemView.findViewById(R.id.displayImage)

        }

    }
}