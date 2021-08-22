package com.example.newsapi.room

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.newsapi.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class NewsDaoTestAndroid {

    private lateinit var database: AppDatabase
    private lateinit var dao: NewsDao


    @Before
    fun setup(){
        database = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java).allowMainThreadQueries().build()
        dao = database.newsDao()
    }

    @After
    fun teardown(){
        database.close()
    }

    @Test
    fun insertNews() = runBlockingTest{
           val news = News("csk","fedfdslk","dcdksjnkc","dsd","zee","12")
           dao.insert(news)
        val allNews = dao.getAll().getOrAwaitValue()
        assertThat(allNews).contains(news)
    }
}