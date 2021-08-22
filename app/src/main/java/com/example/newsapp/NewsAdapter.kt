package com.example.newsapi

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.FrameStats
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapi.models.Article
import com.example.newsapi.room.News
import com.example.newsapp.ui.fragments.BreakingNewsFragmentDirections

class NewsAdapter(val itemClick: (News) -> Unit) : RecyclerView.Adapter<CustomViewHolder>() {

    var allNews = listOf<News>()

    fun setNews(allNews : List<News>){
       this.allNews = allNews
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout,parent, false)
        return CustomViewHolder(view)
    }

    override fun getItemCount(): Int {
        return allNews.size
    }
    private var onItemClickListener : ((News) -> Unit)?= null

    fun setOnItemClickListener(listener : (News) -> Unit){
        onItemClickListener = listener
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val media = allNews[position].imageURL
        val context = holder.itemView.getContext();
        if (media !== null) {
            Glide.with(context)
                .load(media)
                .into(holder.image)
        } else {
            holder.image.setImageResource(R.drawable.ic_launcher_background)
        }
        holder.title.text = allNews[position].title
        holder.description.text = allNews[position].description

        holder.itemView.setOnClickListener{
            Toast.makeText(context,"news clicked No. $position",Toast.LENGTH_LONG).show()
           /* val intent = Intent(context,NewsDetailActivity::class.java)
            intent.putExtra("URL",allNews[position].url)
            startActivity(context,intent,null)
            setOnItemClickListener{
                onItemClickListener?.let { it(allNews[position]) }
            }
                holder.itemView.findNavController().
                navigate(BreakingNewsFragmentDirections.
                actionBreakingNewsFragmentToArticleFragment(allNews[position]))*/
            itemClick(allNews[position])
        }
    }
    //Lamda function

   /* fun updateList(newList : List<News>){
          allNews.clear()
         allNews.addAll(newList)
        notifyDataSetChanged()
    }*/
}