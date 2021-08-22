package com.example.newsapi.Dagger

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.newsapi.room.AppDatabase
import com.example.newsapi.room.NewsDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

//@Module(includes = [AppModule::class])
@Module
class DBModule() {

    @Singleton
    @Provides
    internal fun provideRoomDataBase(context: Context) : AppDatabase{
        return AppDatabase.getInstance(context)
    }

    @Singleton
    @Provides
    internal fun provideDao(db : AppDatabase) : NewsDao{
        return db.newsDao()
    }
}