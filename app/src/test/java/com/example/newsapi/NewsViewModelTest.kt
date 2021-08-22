package com.example.newsapi

import androidx.lifecycle.MutableLiveData
import com.example.newsapi.repository.NewsRepository
import com.example.newsapi.room.News
import com.example.newsapi.viewModel.NewsViewModel
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import kotlin.coroutines.suspendCoroutine

@RunWith(MockitoJUnitRunner::class)
class NewsViewModelTest{

    lateinit var viewModel: NewsViewModel

    lateinit var repository: NewsRepository

    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)
        repository = Mockito.mock(NewsRepository::class.java)
        viewModel = NewsViewModel(repository)
    }
    @Test
    fun `get list of news from repository fetchNewsList function`() {
        val news = News("", "", "", "")
        val newsList = ArrayList<News>()
        newsList.add(news)
        val data = MutableLiveData<ArrayList<News>>()
        data.postValue(newsList)

        `when`(repository.fetchNewsList()).thenReturn(data)
        assertEquals(data.value, viewModel.getNewsList().value)
    }

}