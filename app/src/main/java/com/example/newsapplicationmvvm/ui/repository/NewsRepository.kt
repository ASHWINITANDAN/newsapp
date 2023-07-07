package com.example.newsapplicationmvvm.ui.repository

import com.example.newsapplicationmvvm.ui.api.RetrofitInstance
import com.example.newsapplicationmvvm.ui.db.ArticleDatabase


//Purpose of repository --> To get the data from database and remote data source (API)
class NewsRepository(
    val db: ArticleDatabase
) {
    // fun to get breaking news from API
    suspend fun getBreakingNews(countryCode: String, pageNumber: Int) =
        RetrofitInstance.api.getBreakingNews(countryCode, pageNumber)

}