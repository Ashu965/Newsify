package com.example.newsapi.models

import com.example.newsapi.models.Article

data class NewsAPIResponse(
        val articles: ArrayList<Article>,
        val status: String,
        val totalResults: Int
    )
{
}