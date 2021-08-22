package com.example.newsapi.models

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


data class Article(
        val author: String,
        val content: String,
        val description: String,
        val publishedAt: String,
        val source: SourceObj,
        val title: String,
        val url: String,
        val urlToImage: String
)  {

}