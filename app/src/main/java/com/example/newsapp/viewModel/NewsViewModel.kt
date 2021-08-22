package com.example.newsapi.viewModel

import android.app.Application
import android.util.Log
import androidx.annotation.MainThread
import androidx.lifecycle.*
import androidx.test.core.app.ActivityScenario.launch
import com.example.newsapi.models.NewsAPIResponse
import com.example.newsapi.repository.NewsRepository
import com.example.newsapi.room.AppDatabase
import com.example.newsapi.room.News
import com.example.newsapp.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

class NewsViewModel @Inject constructor(val repository: NewsRepository) : ViewModel() {
    val breakingNewsLiveData : MutableLiveData<Resource<NewsAPIResponse>> = MutableLiveData()
    var breakingNewsPage = 1
    var breakingNewsResponse : NewsAPIResponse?= null

     val searchNewsLiveData : MutableLiveData<Resource<NewsAPIResponse>> = MutableLiveData()
    var searchNewsPage = 1
    var searchNewsResponse : NewsAPIResponse?= null


    init {
        getBreakingNews()
    }
  /*  fun getNewsList(): MutableLiveData<ArrayList<News>> {
        return repository.fetchNewsList()
    }
    fun searchNewsList(searchQuery : String) : MutableLiveData<ArrayList<News>>{
        return repository.searchNews(searchQuery)
}*/
    fun saveArticle(news: News) = viewModelScope.launch {
        repository.insert(news)
    }

    fun getSavedNews() = repository.getSavedNews()

    fun deleteArticle(news: News) = viewModelScope.launch {
        repository.delete(news)
    }

    fun getBreakingNews() = viewModelScope.launch {
            breakingNewsLiveData.postValue(Resource.Loading())
            val response = repository.getBreakingNews(breakingNewsPage)
            breakingNewsLiveData.postValue(handleBreakingNewsResponse(response))
        }

    fun getSearchNews(searchQuery : String) = viewModelScope.launch {
            searchNewsLiveData.postValue(Resource.Loading())
            val response = repository.searchNews(searchQuery,searchNewsPage)
            Log.d("response",response.toString())
            searchNewsLiveData.postValue(handleSearchNewsResponse(response))
    }


    fun handleBreakingNewsResponse(response : Response<NewsAPIResponse>) : Resource<NewsAPIResponse>{
        if(response.isSuccessful){
            response.body()?.let { resultResponse ->
              breakingNewsPage++
              if(breakingNewsResponse==null)
                  breakingNewsResponse = resultResponse
                else{
                    val oldArticles = breakingNewsResponse?.articles
                    val newArticle = resultResponse?.articles
                     oldArticles?.addAll(newArticle)
              }
              return Resource.Success(breakingNewsResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    fun handleSearchNewsResponse(response : Response<NewsAPIResponse>) : Resource<NewsAPIResponse>{
        if(response.isSuccessful){
            response.body()?.let { resultResponse ->
                searchNewsPage++
                if(searchNewsResponse==null)
                    searchNewsResponse = resultResponse
                else{
                    val oldArticles = searchNewsResponse?.articles
                    val newArticle = resultResponse?.articles
                    oldArticles?.addAll(newArticle)
                }
                return Resource.Success(searchNewsResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }













   /* lateinit var repository: NewsRepository

    lateinit var newslist : MutableLiveData<List<News>>

    init {
       val dao = AppDatabase.getInstance(application).newsDao()
        repository = NewsRepository(dao)
        newslist = repository.loadNews()

    }*/
}


