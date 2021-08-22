package com.example.newsapi.room

import android.content.ClipData.Item
import android.content.ContentValues
import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity
data class News(
    @PrimaryKey(autoGenerate = true)
    var id : Int? = null,
    val title : String,
    val description : String?,
    val imageURL : String?,
    val url : String,
    val source : String,
    val date : String
) : Serializable
{
   /* companion object {
        fun fromContentValues(values: ContentValues): News? {
            return News(values.getAsString("title"),
                    values.getAsString("description"),values.getAsString("imageURL"),values.getAsString("url"))
        }
    }*/
}