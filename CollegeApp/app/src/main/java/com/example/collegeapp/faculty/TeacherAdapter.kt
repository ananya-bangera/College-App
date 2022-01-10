package com.example.collegeapp.faculty
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.collegeapp.R
import com.squareup.picasso.Picasso
import javax.annotation.Nullable

class TeacherAdapter : RecyclerView.Adapter<TeacherAdapter.TeacherViewAdapter> {

    private var list :List<TeacherData>
    private var context: Context
    private var category: String

    constructor(list: List<TeacherData>,context: Context,category: String) : super() {
        this.context = context
        this.list = list
        this.category=category
    }


    @Nullable
     override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeacherViewAdapter{
        var view: View = LayoutInflater.from(context).inflate(R.layout.faculty_item_layout,parent,false)

        return TeacherViewAdapter(view)
    }

    override fun onBindViewHolder(holder: TeacherAdapter.TeacherViewAdapter, position: Int) {

        var item: TeacherData =list.get(position)
        holder.name.setText(item.name)
        holder.email.setText(item.email)
        holder.post.setText(item.post)
        try {
            Picasso.get().load(item.image).into(holder.imageView)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        holder.update.setOnClickListener(object:View.OnClickListener {
            override fun onClick(view:View){

                var intent :  Intent = Intent(context,UpdateTeacherActivity::class.java)
                intent.putExtra("name",item.name)
                intent.putExtra("email",item.email)
                intent.putExtra("post",item.post)
                intent.putExtra("image",item.image)
                intent.putExtra("key",item.key)
                intent.putExtra("category",category)
                context.startActivity(intent)
            }
        })

    }

    override fun getItemCount(): Int {
        return list.size
    }
    class TeacherViewAdapter : RecyclerView.ViewHolder {
        public lateinit var name:TextView
        public lateinit var email:TextView
        public lateinit var post:TextView
        public lateinit var update: Button
        public lateinit var imageView: ImageView

        constructor(itemView: View) : super(itemView){
            name= itemView.findViewById(R.id.teacherName)
            email= itemView.findViewById(R.id.teacherEmail)
            post= itemView.findViewById(R.id.teacherPost)
            update= itemView.findViewById(R.id.teacherUpdate)
            imageView= itemView.findViewById(R.id.teacherImage)
        }
    }



}