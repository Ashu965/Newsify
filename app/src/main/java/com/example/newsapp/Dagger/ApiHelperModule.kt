package com.example.newsapi.Dagger

import android.app.Application
import android.content.Context
import com.example.newsapi.api.NewsAPI
import com.example.newsapi.repository.NewsRepository
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

//@Module(includes = [AppModule::class])
@Module
    class ApiHelperModule {

        val BASE_URL = "https://newsapi.org/";

        @Provides
        @Singleton
        internal fun provideOkhttpClient( context: Context): OkHttpClient {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder()
            client.addInterceptor(interceptor)
            return client.build();
        }

        @Provides
        @Singleton
        internal fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
            return Retrofit.Builder().baseUrl(BASE_URL).client(OkHttpClient())
                .addConverterFactory(GsonConverterFactory.create()).build()
        }

    @Provides
    @Singleton
    internal fun getNewsApi(retroFit: Retrofit): NewsAPI {
            return retroFit.create(NewsAPI::class.java)
        }


    }
