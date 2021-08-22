package com.example.newsapi

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

    val title = itemView.findViewById<TextView>(R.id.tvTitle)
    val description = itemView.findViewById<TextView>(R.id.tvDescription)
    val image =  itemView.findViewById<ImageView>(R.id.ivArticleImage)
    val source = itemView.findViewById<TextView>(R.id.tvSource)
    val date = itemView.findViewById<TextView>(R.id.tvPublishedAt)

}