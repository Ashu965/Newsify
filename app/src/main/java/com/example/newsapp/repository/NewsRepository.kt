package com.example.newsapi.repository

import android.content.Intent
import android.util.Log
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.newsapi.NewsAdapter
import com.example.newsapi.api.NewsAPI
import com.example.newsapi.models.NewsAPIResponse
import com.example.newsapi.room.News
import com.example.newsapi.room.NewsDao
import dagger.Component
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.suspendCoroutine

open class NewsRepository @Inject constructor(val newsAPI: NewsAPI,val newsDao: NewsDao) {

    /*val allNews : LiveData<List<News>> = when(newsdao.size()) {
         0 ->  makeNetworkCall()
         else -> newsdao.getAll()
    }*/

    //val breakingNewsLiveData : MutableLiveData<ArrayList<News>> = MutableLiveData()
   // val searchNewsLiveData : MutableLiveData<ArrayList<News>> = MutableLiveData()

   // @Inject
   // lateinit var newsAPI: NewsAPI
   // @Inject
  //  lateinit var newsdao:NewsDao

       /* fun fetchNewsList() : MutableLiveData<ArrayList<News>>{
        val listOfNews = ArrayList<News>()

           newsAPI.getBreakingNews().enqueue(object : Callback<NewsAPIResponse> {
            override fun onFailure(call: Call<NewsAPIResponse>?, t: Throwable?){
                Log.d("api", t?.message.toString())
            }

            override fun onResponse(
                call: Call<NewsAPIResponse>?,
                response: Response<NewsAPIResponse>?
            ) {
                for (article in response?.body()?.articles!!) {
                    listOfNews.add(
                        News(
                            null,
                            article.title,
                            article.description,
                            article.urlToImage,
                            article.url,
                            article.source.name,
                            article.publishedAt
                        )
                    )
                }
                breakingNewsLiveData.postValue(listOfNews)
            }
        })
        return breakingNewsLiveData
    }*/



     /*fun searchNews(searchQuery : String) : MutableLiveData<ArrayList<News>>{
         val listOfNews = ArrayList<News>()
         newsAPI.searchForNews(searchQuery).enqueue(object : Callback<NewsAPIResponse>{
             override fun onFailure(call: Call<NewsAPIResponse>?, t: Throwable?){
                 Log.d("api", t?.message.toString())
             }

             override fun onResponse(
                 call: Call<NewsAPIResponse>?,
                 response: Response<NewsAPIResponse>?
             ) {
                 for (article in response?.body()?.articles!!) {
                     listOfNews.add(
                         News(
                             null,
                             article.title,
                             article.description,
                             article.urlToImage,
                             article.url,
                             article.source.name,
                             article.publishedAt
                         )
                     )
                 }
                 searchNewsLiveData.postValue(listOfNews)
             }
         })
         return  searchNewsLiveData
     }*/

    suspend fun getBreakingNews(pagenumber : Int) = newsAPI.getBreakingNews(pageNumber = pagenumber)

    suspend fun searchNews(searchQuery : String , pageNumber : Int ) =
        newsAPI.searchForNews(searchQuery = searchQuery,pageNumber = pageNumber)

    suspend fun insert(news: News) =  newsDao.insert(news)

    fun getSavedNews() = newsDao.getAll()

    suspend fun  delete(news : News) = newsDao.deleteArticle(news)


















   /* fun insert(news : News){
        newsdao.insert(news)
    }
    fun deleteAll(){
        newsdao.deleteAll()
    }
    fun insertAll(listOfNews : List<News>){
        newsdao.insertAll(listOfNews)
    }

     fun loadNews() : MutableLiveData<List<News>>{
        val URL = "https://newsapi.org/"
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()

        val retrofit = Retrofit.Builder().baseUrl(URL).client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create()).build()

        val newsInterface = retrofit.create(NewsAPI::class.java)
        val listOfNews = ArrayList<News>()
        val callNewsList = newsInterface.getListOfMovies()
        var newsList = MutableLiveData<List<News>>()
        callNewsList.enqueue(object : Callback<NewsAPIResponse> {
            override fun onFailure(call: Call<NewsAPIResponse>?, t: Throwable?) {
                Log.d("api", t?.message.toString())
            }

            override fun onResponse(
                    call: Call<NewsAPIResponse>?,
                    response: Response<NewsAPIResponse>?
            ) {
                val listOfArticles = response?.body()?.articles
                for (article in response?.body()?.articles!!) {
                    listOfNews.add(News(article.title, article.description, article.urlToImage, article.url))
                }
              newsList.postValue(listOfNews)
            }
        })
         //newsList.postValue(listOfNews)
        return newsList} */

}