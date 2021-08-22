package com.example.newsapi.Dagger

import android.content.Context
import com.example.newsapp.ui.NewsActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DBModule::class,ApiHelperModule::class])
interface NewsComponent{
    fun inject(activity : NewsActivity)


    @Component.Factory
    interface Factory {
        // With @BindsInstance, the Context passed in will be available in the graph
        fun create(@BindsInstance context: Context): NewsComponent
    }

}