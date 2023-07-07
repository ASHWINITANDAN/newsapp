package com.example.newsapplicationmvvm.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.newsapplicationmvvm.R
import com.example.newsapplicationmvvm.ui.NewsActivity
import com.example.newsapplicationmvvm.ui.NewsViewModel


class SavedNewsFragment : Fragment() {

    lateinit var viewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_saved_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(NewsViewModel::class.java)
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            SavedNewsFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}