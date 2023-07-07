package com.example.newsapplicationmvvm.ui.api

import com.example.newsapplicationmvvm.ui.models.NewsResponse
import com.example.newsapplicationmvvm.ui.util.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPI {

    //get request to get the data from the API
    @GET("v2/top-headlines") // v2/top-headlines --> this comes from the get url -- the part after the base url of API
    // function that get the breaking news, we create it in coroutines - to call it asynchronously -- to create a function in coroutine we prefix it with suspend keyword
    suspend fun getBreakingNews(
        //if the parameters are from request then we need to write the annotation @Query
        @Query("country")
        countryCode: String = "in",
        @Query("page")
        pageNumber: Int = 1,
        @Query("apiKey")
        apiKey: String = API_KEY
    ): Response<NewsResponse>

    @GET("v2/top-headlines")
    suspend fun searchForNews(
        @Query("q")
        searchQuery: String,
        @Query("page")
        pageNumber: Int = 1,
        @Query("apiKey")
        apiKey: String = API_KEY
    ): Response<NewsResponse>


}