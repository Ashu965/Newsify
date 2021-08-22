package com.example.newsapi.ContentProvider

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import com.example.newsapi.room.News

class ContentMapper(private var mContext: Context) {

        private var contentResolver: ContentResolver = mContext.contentResolver
        val URI = Uri.parse("content://com.example.newsapi.ContentProvider.NewsContentProvider/NewsTable")

        val allNews: List<News>
            get() {
                val listOfNews = ArrayList<News>()
                val projection = arrayOf<String>("id","title", "description","imageURL","url")
                val cursor = contentResolver.query(URI, projection, null, null, null)
                if (cursor != null && cursor.count > 0) {
                    while (cursor.moveToNext()) {
                        val news = News(null,cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),"zee","12")
                        listOfNews.add(news)
                    }
                }
                return listOfNews
            }
       fun insertNews(news: News) {
        val contentValues = ContentValues()
        contentValues.put("title",news.title)
        contentValues.put("description",news.description)
        contentValues.put("imageURL",news.imageURL)
        contentValues.put("url",news.url)
        contentResolver.insert(URI, contentValues)
    }

    fun deleteAll(){
        contentResolver.delete(URI,null,null)
    }
}