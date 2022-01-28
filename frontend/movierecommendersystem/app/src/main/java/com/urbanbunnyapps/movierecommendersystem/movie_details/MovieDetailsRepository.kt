package com.urbanbunnyapps.movierecommendersystem.movie_details

import androidx.lifecycle.LiveData
import com.urbanbunnyapps.movierecommendersystem.data.api.MovieDBInterface
import com.urbanbunnyapps.movierecommendersystem.data.repository.MovieDetailsNetworkDataSource
import com.urbanbunnyapps.movierecommendersystem.data.repository.NetworkState
import com.urbanbunnyapps.movierecommendersystem.data.vo.MovieDetails
import io.reactivex.disposables.CompositeDisposable

class MovieDetailsRepository (private val apiService: MovieDBInterface){

    lateinit var movieDetailsNetworkDataSource: MovieDetailsNetworkDataSource

    fun fetchSingleMovieDetails(compositeDisposable: CompositeDisposable, movieId: Int): LiveData<MovieDetails>{
        movieDetailsNetworkDataSource = MovieDetailsNetworkDataSource(apiService, compositeDisposable)
        movieDetailsNetworkDataSource.fetchMovieDetails(movieId)
        return movieDetailsNetworkDataSource.downloadedMovieResponse
    }

    fun getMovieDetailsNetworkState(): LiveData<NetworkState> {
        return movieDetailsNetworkDataSource.networkState
    }
}