package com.example.newsapplicationmvvm.ui.api

import com.example.newsapplicationmvvm.ui.util.Constants.Companion.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {

    companion object{

        // lazy means only initialized for this block of code
        private val retrofit by lazy  {
            val logging = HttpLoggingInterceptor() // useful for efficient debugging -- for these we added dependencies in gradle file
            logging.setLevel(HttpLoggingInterceptor.Level.BODY) // By adding this line of code we see the actual body of the response
            //we can use this interceptor to create a network client
            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()) // ConverterFactory is to determine how the response should be interpreted to kotlin objects
                .client(client)
                .build()
        }

        val api by lazy {
            retrofit.create(NewsAPI::class.java)
        }
    }
}