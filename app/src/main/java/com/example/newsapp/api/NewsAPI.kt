package com.example.newsapi.api

import com.example.newsapi.models.NewsAPIResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPI {

  @GET("v2/top-headlines")
      suspend fun getBreakingNews(
      @Query("country")
      countryCode: String = "us",
      @Query("page")
      pageNumber: Int = 1,
      @Query("apiKey")
      apiKey: String =  "afb6f7f50675434d8dcef0a7f6491db1"
  ): Response<NewsAPIResponse>

  @GET("v2/everything")
   suspend fun searchForNews(
    @Query("q")
    searchQuery: String,
    @Query("page")
    pageNumber: Int = 1,
    @Query("apiKey")
    apiKey: String = "afb6f7f50675434d8dcef0a7f6491db1"
  ): Response<NewsAPIResponse>
}