package com.example.newsapplicationmvvm.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    private fun handleBreakingNewsResponse(response: Response<NewsResponse>) : Resource<NewsResponse>{
        if(response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())

    }
}