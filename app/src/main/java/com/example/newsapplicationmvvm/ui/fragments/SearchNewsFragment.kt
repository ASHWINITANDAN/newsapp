package com.example.newsapplicationmvvm.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ProgressBar
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapplicationmvvm.R
import com.example.newsapplicationmvvm.R.*
import com.example.newsapplicationmvvm.ui.NewsActivity
import com.example.newsapplicationmvvm.ui.NewsViewModel
import com.example.newsapplicationmvvm.ui.NewsViewModelProviderFactory
import com.example.newsapplicationmvvm.ui.adapters.NewsAdapter
import com.example.newsapplicationmvvm.ui.db.ArticleDatabase
import com.example.newsapplicationmvvm.ui.repository.NewsRepository
import com.example.newsapplicationmvvm.ui.util.Constants.Companion.SEARCH_NEWS_TIME_DELAY
import com.example.newsapplicationmvvm.ui.util.Resource
import com.google.android.material.tabs.TabLayout.TabGravity
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SearchNewsFragment : Fragment() {

    lateinit var viewModel: NewsViewModel
    lateinit var newsAdapter: NewsAdapter
    lateinit var paginationProgressBarSN: ProgressBar
    lateinit var rvSearchNews: RecyclerView
    lateinit var etSearch: EditText

    val TAG = "SearchNewsFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val newsRepository = NewsRepository(ArticleDatabase(requireActivity()))
        val viewModelProviderFactory = NewsViewModelProviderFactory(newsRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(NewsViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(layout.fragment_search_news, container, false)
        rvSearchNews = view.findViewById(R.id.rvSearchNews)
        paginationProgressBarSN = view.findViewById(R.id.paginationProgressBarSN)
        etSearch = view.findViewById(R.id.etSearch)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(NewsViewModel::class.java)
        setupRecyclerView()

        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }
            findNavController().navigate(
                R.id.action_searchNewsFragment_to_articleFragment,
                bundle
            )
        }

        var job: Job? = null
        etSearch.addTextChangedListener { editable ->
            job?.cancel()
            job = MainScope().launch {
                delay(SEARCH_NEWS_TIME_DELAY)
                editable?.let {
                    if (editable.toString().isNotEmpty()) {
                        viewModel.searchNews(editable.toString())

                    }
                }

            }
        }

        viewModel.searchNews.observe(viewLifecycleOwner, Observer {response ->
            when(response){
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { newsResponse ->
                        newsAdapter.differ.submitList(newsResponse.articles)

                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Log.e(TAG, "An error occured: $message")
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }

            }

        })

    }

    private fun hideProgressBar() {
        paginationProgressBarSN.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        paginationProgressBarSN.visibility = View.VISIBLE
    }


    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter()
        rvSearchNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }

    }


}