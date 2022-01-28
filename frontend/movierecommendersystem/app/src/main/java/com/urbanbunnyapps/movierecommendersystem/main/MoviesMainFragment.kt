package com.urbanbunnyapps.movierecommendersystem.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.urbanbunnyapps.movierecommendersystem.R
import com.urbanbunnyapps.movierecommendersystem.data.api.MovieDBClient
import com.urbanbunnyapps.movierecommendersystem.data.api.MovieDBInterface
import com.urbanbunnyapps.movierecommendersystem.data.repository.NetworkState


class MoviesMainFragment : Fragment() {

    private lateinit var viewModel: MainActivityViewModel
    lateinit var movieRepository: MoviePageListRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movies_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val apiService : MovieDBInterface = MovieDBClient.getClient()
        movieRepository = MoviePageListRepository(apiService)
        viewModel = getViewModel()

        val movieAdapter = MoviesPageListAdapter(requireContext())

        val gridLayoutManager = GridLayoutManager(activity, 3)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup(){
            override fun getSpanSize(position: Int): Int {
                val viewType: Int = movieAdapter.getItemViewType(position)
                if (viewType == movieAdapter.MOVIE_VIEW_TYPE) return 1
                else return 3
            }
        }

        val recyclerView: RecyclerView = view.findViewById(R.id.movieRecyclerView)
        recyclerView.layoutManager = gridLayoutManager
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = movieAdapter

        viewModel.moviePageList.observe(viewLifecycleOwner, Observer {
            movieAdapter.submitList(it)
        })

        val movieListProgressBar: ProgressBar = view.findViewById(R.id.main_progress_bar)
        val textErrorList: TextView = view.findViewById(R.id.main_text_error)

        viewModel.networkState.observe(viewLifecycleOwner, Observer {
            movieListProgressBar.visibility = if (viewModel.listIsEmpty() && it == NetworkState.LOADING) View.VISIBLE else View.GONE
            textErrorList.visibility = if (viewModel.listIsEmpty() && it == NetworkState.ERROR) View.VISIBLE else View.GONE

            if (!viewModel.listIsEmpty()){
                movieAdapter.setNetworkState(it)
            }
        })
    }

    private fun getViewModel(): MainActivityViewModel{
        return ViewModelProvider(this, object : ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return MainActivityViewModel(movieRepository) as T
            }
        })[MainActivityViewModel::class.java]
    }
}