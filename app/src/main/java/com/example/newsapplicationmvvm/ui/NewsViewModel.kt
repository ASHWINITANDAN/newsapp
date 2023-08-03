package com.example.newsapplicationmvvm.ui

import android.app.DownloadManager.Query
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapplicationmvvm.ui.models.Article
import com.example.newsapplicationmvvm.ui.models.NewsResponse
import com.example.newsapplicationmvvm.ui.repository.NewsRepository
import com.example.newsapplicationmvvm.ui.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(
    val newsRepository: NewsRepository
) : ViewModel() {


    val breakingNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var breakingNewsPage = 1
    var breakingNewsResponse: NewsResponse? = null   // We are setting it nullable because we don't know what the response is going to be

    val searchNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var searchNewsPage = 1
    var searchNewsResponse: NewsResponse? = null

    init {
        getBreakingNews("in")
    }

    // to execute the API call from the repository
    fun getBreakingNews(countryCode: String) = viewModelScope.launch {  // As we specify scope here this coroutine is alive until our viewModel is alive

        breakingNews.postValue(Resource.Loading())      //Here we post the response to the live data
        //Actual response
        val response = newsRepository.getBreakingNews(countryCode, breakingNewsPage)
        breakingNews.postValue(handleBreakingNewsResponse(response))        //Here we post the response success or error state

    }

    fun searchNews(searchQuery: String) = viewModelScope.launch {
        searchNews.postValue(Resource.Loading())
        val response = newsRepository.searchNews(searchQuery, searchNewsPage)
        searchNews.postValue(handleSearchNewsResponse(response))

    }

    private fun handleBreakingNewsResponse(response: Response<NewsResponse>) : Resource<NewsResponse>{
        if(response.isSuccessful) {
            response.body()?.let { resultResponse ->
                // After getting the successful response we increment the breaking news page
                breakingNewsPage++
                if(breakingNewsResponse == null) {      // Check for first response
                    breakingNewsResponse = resultResponse
                } else {
                    val oldArticles = breakingNewsResponse?.articles        // If we loaded more than one page already
                    val newArticles = resultResponse.articles
                    oldArticles?.addAll(newArticles)
                }
                return Resource.Success(breakingNewsResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())

    }

    private fun handleSearchNewsResponse(response: Response<NewsResponse>) : Resource<NewsResponse>{
        if(response.isSuccessful) {
            response.body()?.let { resultResponse ->
                // After getting the successful response we increment the breaking news page
                searchNewsPage++
                if(searchNewsResponse == null) {      // Check for first response
                    searchNewsResponse = resultResponse
                } else {
                    val oldArticles = searchNewsResponse?.articles        // If we loaded more than one page already
                    val newArticles = resultResponse.articles
                    oldArticles?.addAll(newArticles)
                }
                return Resource.Success(searchNewsResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())

    }

    fun saveArticle(article: Article) = viewModelScope.launch {
        newsRepository.upsert(article)
    }

    fun getSavedNews() = newsRepository.getSavedNews() // This function is used to observe the changes in the database

    fun deleteArticle(article: Article) = viewModelScope.launch {
        newsRepository.deleteArticle(article)
    }

}