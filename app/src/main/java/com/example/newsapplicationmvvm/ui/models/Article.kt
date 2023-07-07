package com.example.newsapplicationmvvm.ui.models

import androidx.room.Entity
import androidx.room.PrimaryKey

//@Entity annotation tells android studio that Article class is a table in our database
@Entity(
    tableName = "articles"

)
data class Article(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null, // We set the id as null because not every article have the id, as we get article from retrofit to display in our breaking news fragment but we store some of them only in database.
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val source: Source,
    val title: String,
    val url: String,
    val urlToImage: String
)