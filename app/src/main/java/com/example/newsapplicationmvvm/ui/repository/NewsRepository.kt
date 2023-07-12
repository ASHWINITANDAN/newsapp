package com.example.newsapplicationmvvm.ui.repository

import com.example.newsapplicationmvvm.ui.api.RetrofitInstance
import com.example.newsapplicationmvvm.ui.db.ArticleDatabase
import com.example.newsapplicationmvvm.ui.models.Article


//Purpose of repository --> To get the data from database and remote data source (API)
class NewsRepository(
    val db: ArticleDatabase
) {
    // fun to get breaking news from API
    suspend fun getBreakingNews(countryCode: String, pageNumber: Int) =
        RetrofitInstance.api.getBreakingNews(countryCode, pageNumber)

   // fun to call the API for search
    suspend fun searchNews(searchQuery: String, pageNumber: Int) =
        RetrofitInstance.api.searchForNews(searchQuery, pageNumber)

    // fun to add the article into the database
    suspend fun upsert(article: Article) = db.getArticleDao().upsert(article)

    // fun to get our saved news
    fun getSavedNews() = db.getArticleDao().getAllArticles()

    // fun to delete an article
    suspend fun deleteArticle(article: Article) = db.getArticleDao().deleteArticle(article)

}