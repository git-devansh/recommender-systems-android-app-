package com.urbanbunnyapps.movierecommendersystem.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.urbanbunnyapps.movierecommendersystem.data.api.MovieDBInterface
import com.urbanbunnyapps.movierecommendersystem.data.api.POST_PER_PAGE
import com.urbanbunnyapps.movierecommendersystem.data.repository.MovieDataSource
import com.urbanbunnyapps.movierecommendersystem.data.repository.MovieDataSourceFactory
import com.urbanbunnyapps.movierecommendersystem.data.repository.NetworkState
import com.urbanbunnyapps.movierecommendersystem.data.vo.Movie
import io.reactivex.disposables.CompositeDisposable

class MoviePageListRepository(private val apiService: MovieDBInterface){

    lateinit var moviePagedList: LiveData<PagedList<Movie>>
    lateinit var movieDataSourceFactory: MovieDataSourceFactory

    fun fetchLiveMoviePagedList(compositeDisposable: CompositeDisposable): LiveData<PagedList<Movie>>{
        movieDataSourceFactory = MovieDataSourceFactory(apiService, compositeDisposable)

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(POST_PER_PAGE)
            .build()

        moviePagedList = LivePagedListBuilder(movieDataSourceFactory, config).build()

        return moviePagedList
    }

    fun getNetworkState(): LiveData<NetworkState>{
        return Transformations.switchMap<MovieDataSource, NetworkState>(
            movieDataSourceFactory.moviesLiveDataSource, MovieDataSource::networkState)
    }

}