package com.example.newsapi.ContentProvider

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.example.newsapi.room.AppDatabase
import com.example.newsapi.room.News
import java.lang.IllegalArgumentException
import java.net.URI

    class NewsContentProvider{ //: ContentProvider() {
   /* companion object{
        const val Authority = "com.example.newsapi.ContentProvider.NewsContentProvider"
        val URI_TABLE = Uri.parse("content://com.example.newsapi.ContentProvider.NewsContentProvider/NewsTable")

        private const val News_Table =1
        private const val News_item = 2

        private val matcher = UriMatcher(UriMatcher.NO_MATCH)

        init{
            matcher.addURI(Authority,"NewsTable", News_Table)
            matcher.addURI(Authority,"NewsItem", News_item)
        }

    }
    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return when(matcher.match(uri)){
                News_Table -> {
                   val context = context ?: return null
                    //after inserting value , new id for that particular tuple will be generated.
                   val id : Long = AppDatabase.getInstance(context).newsDao().insert(values?.let{
                       News.fromContentValues(it)}!!)
                      context.contentResolver.notifyChange(uri,null)    // As soon as dataset changes, loader get notified and new values will get queried.
                    // Id that is return after insertion needs to be appended to the uri and then return that uri.
                     ContentUris.withAppendedId(uri,id) //
                }
                News_item -> throw java.lang.IllegalArgumentException("Invalid URL , cannot insert with Id : $uri")
                else -> throw java.lang.IllegalArgumentException("Unknown URI: $uri")
            }
        }

    override fun query(uri: Uri, projection: Array<out String>?, selection: String?, selectionArgs: Array<out String>?, sortOrder: String?): Cursor? {
        val code = matcher.match(uri)
        return if(code == News_item || code == News_Table){
            val context = context ?: return null
            val newsDao = AppDatabase.getInstance(context).newsDao()
            val cursor:Cursor
            cursor = if(code == News_Table){
                newsDao.getAll()
            }
            else{
                newsDao.getById(ContentUris.parseId(uri))
            }
            cursor.setNotificationUri(context.contentResolver,uri)
            cursor
        }
        else{
            throw IllegalArgumentException("Unknown Uri : $uri")
        }
    }

    override fun onCreate(): Boolean {
       return true
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int {
        return when(matcher.match(uri)){
            News_item  -> throw java.lang.IllegalArgumentException("Invalid Uri , cannot update without $uri")
            News_Table -> {
                val context = context ?: return 0
                val news : News= values?.let {
                    News.fromContentValues(it)
                }!!
                val count : Int = AppDatabase.getInstance(context).newsDao().updateNews(news)
                context.contentResolver.notifyChange(uri,null)
                count
            }
            else -> throw java.lang.IllegalArgumentException("Unknown URI: $uri")
        }
    }


    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        return when(matcher.match(uri)){
             News_Table -> {
                 val context = context ?: return 0
                 val count : Int = AppDatabase.getInstance(context).newsDao().deleteAll()
                 context.contentResolver.notifyChange(uri,null)
                 count
             }
             else -> throw java.lang.IllegalArgumentException("Unknown URI: $uri")

         }
    }

    override fun getType(uri: Uri): String? {
        TODO("Not yet implemented")
    }*/

}