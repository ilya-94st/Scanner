package com.example.scanner

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.scanner.DataBases.User
import java.io.ByteArrayOutputStream

class CustomRecucler():
    RecyclerView.Adapter<CustomRecucler.MyHolder>() {
    var userList = emptyList<User>()
    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        var textView: TextView = itemView.findViewById(R.id.text)
        var textView2: TextView = itemView.findViewById(R.id.text2)
        var textView3: TextView = itemView.findViewById(R.id.text3)
        var imageView: ImageView = itemView.findViewById(R.id.imageButton)

    }

    private fun decodePhoto(encodedString: String?): Bitmap? {
        val decodedString: ByteArray = Base64.decode(encodedString, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(
            decodedString, 0,
            decodedString.size
        )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recucler_view,parent,false)
        return MyHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val currentItem = userList[position]
        holder.textView.text = currentItem.name
        holder.textView2.text = currentItem.phone
        holder.textView3.text = currentItem.email
        holder.imageView.setImageBitmap(decodePhoto(currentItem.image))
    }

    override fun getItemCount(): Int {
        return userList.size
    }
    fun saveData(user: List<User>) {
       this.userList = user
        notifyDataSetChanged()
    }
}