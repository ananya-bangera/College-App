package com.example.collegeapp.ebook

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.collegeapp.R
import com.example.collegeapp.notice.NoticeData
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso

class EbookAdapter: RecyclerView.Adapter<EbookAdapter.EbookViewHolder> {

    private lateinit var context: Context
    private lateinit var list:ArrayList<EbookData>

    constructor(context: Context,list: ArrayList<EbookData>) : super() {
        this.context = context
        this.list = list
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EbookViewHolder {
        var view:View = LayoutInflater.from(context).inflate(R.layout.ebook_item_feed,parent,false)
        return EbookViewHolder(view)
    }



    override fun onBindViewHolder(holder: EbookViewHolder, position: Int) {

        var currentItem: EbookData = list.get(position)

        holder.eBookName.setText(currentItem.name)
        holder.itemView.setOnClickListener(object:View.OnClickListener {

            override fun onClick(view:View){

//                Toast.makeText(context,(currentItem.name),Toast.LENGTH_SHORT)
                var intent = Intent(context,PdfViewerActivity::class.java)
                intent.putExtra("pdfUrl",currentItem.pdfUrl)
                context.startActivity(intent)
            }

        })
        holder.eBookDownload.setOnClickListener(object:View.OnClickListener {

            override fun onClick(view:View){
//                Toast.makeText(context,"Download",Toast.LENGTH_SHORT)
                var intent = Intent(Intent.ACTION_VIEW)
                intent.setData(Uri.parse(currentItem.pdfUrl))
                context.startActivity(intent)
            }

        })

    }

    override fun getItemCount(): Int {
        return list.size
    }

    public class EbookViewHolder:RecyclerView.ViewHolder{

        lateinit var eBookName: TextView
        lateinit var eBookDownload:Button


        constructor(itemView: View) : super(itemView){
            eBookName = itemView.findViewById(R.id.eBookName)
            eBookDownload = itemView.findViewById(R.id.eBookDownload)

        }

    }
}