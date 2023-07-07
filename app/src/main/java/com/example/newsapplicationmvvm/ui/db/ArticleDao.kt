package com.example.newsapplicationmvvm.ui.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newsapplicationmvvm.ui.models.Article


@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)    //Inside this annotation we set onConflict Strategy to replace that is if the same data found in the db it got replaced
    suspend fun upsert(article: Article): Long

    @Query("SELECT * FROM articles")
    // suspend function didn't work here cause below function works with live data
    // live data -- when the data is changes in the database, observers got notified and the data is updated whether the orientation is changed, this will give the most updated data,
    // ViewModel doesn't handle the orientation(device rotation) change.
    // If we rotate our device the ViewModel is not recreated

    fun getAllArticles(): LiveData<List<Article>>  // This function return the live data, so when the articles list changes livedata inform all of its observer.

    @Delete
    suspend fun deleteArticle(article: Article)
}