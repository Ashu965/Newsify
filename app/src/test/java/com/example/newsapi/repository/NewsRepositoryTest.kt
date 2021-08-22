package com.example.newsapi.repository

import androidx.lifecycle.MutableLiveData
import com.example.newsapi.api.NewsAPI
import com.example.newsapi.models.Article
import com.example.newsapi.models.NewsAPIResponse
import com.example.newsapi.models.SourceObj
import com.example.newsapi.room.News
import com.example.newsapi.room.NewsDao
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.test.runBlockingTest
import okio.Source
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.any
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@RunWith(MockitoJUnitRunner::class)
class NewsRepositoryTest {
    @Mock
    lateinit var newsApi : NewsAPI
    @Mock
    lateinit var newsDao: NewsDao

    lateinit var repository: NewsRepository

    @Before
    fun setup(){
        repository = NewsRepository(newsApi,newsDao)
    }

    @Test
    fun testApiResponse() {

        // val call = newsApi.getListOfMovies()
        val mockedCall: Call<NewsAPIResponse> =
            Mockito.mock(Call::class.java) as Call<NewsAPIResponse>

        Mockito.`when`(newsApi.getListOfMovies()).thenReturn(mockedCall)

        Mockito.doAnswer { invocation ->
            val callback: Callback<NewsAPIResponse> =
                invocation.getArgument<Callback<NewsAPIResponse>>(0)

            val article = Article("ashu", "", "", "", SourceObj(1, ""), "", "", "")
            val articles = listOf<Article>(article)
            callback.onResponse(mockedCall, Response.success(NewsAPIResponse(articles, "Ok", 0)))
            // or callback.onResponse(mockedCall, Response.error(404. ...);
            // or callback.onFailure(mockedCall, new IOException());
            null
        }.`when`(mockedCall).enqueue(any(Callback::class.java) as Callback<NewsAPIResponse>?)

        repository.fetchNewsList()
        verify(
            mockedCall,
            times(1)
        ).enqueue(any(Callback::class.java) as Callback<NewsAPIResponse>?)

        val data = MutableLiveData<ArrayList<News>>()
        val news = News("","","","")
        val newslist = ArrayList<News>()
        data.postValue(newslist)

        assertEquals(data.value, repository.fetchNewsList().value)
        // inject mocked ApiInterface to your presenter
        // and then mock view and verify calls (and eventually use ArgumentCaptor to access call parameters)
    }
}