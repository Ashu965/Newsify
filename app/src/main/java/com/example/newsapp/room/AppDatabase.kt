package com.example.newsapi.room

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(News::class),version = 1,exportSchema = false)
abstract  class AppDatabase : RoomDatabase() {
     abstract fun newsDao() : NewsDao
     companion object{

          @Volatile
          private var instance : AppDatabase? = null

          fun getInstance(context : Context) : AppDatabase{
               return instance ?: synchronized(this) {
                    val Instance = Room.databaseBuilder(
                         context.applicationContext,
                         AppDatabase::class.java,
                         "word_database"
                    ).allowMainThreadQueries().build()
                    instance = Instance
                    // return instance
                    instance
               }!!
          }
     }
}