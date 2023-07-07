package com.example.newsapplicationmvvm.ui.models

import com.example.newsapplicationmvvm.ui.models.Article

data class  NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)