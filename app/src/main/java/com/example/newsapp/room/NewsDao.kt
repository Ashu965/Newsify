package com.example.newsapi.room

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import javax.inject.Inject

@Dao
interface NewsDao {

    @Query("Select * from News")
    fun getAll() : LiveData<List<News>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(news : News) : Long

   @Delete
   suspend fun deleteArticle(news : News)
}