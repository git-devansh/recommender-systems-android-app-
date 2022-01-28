package com.urbanbunnyapps.movierecommendersystem.movie_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.urbanbunnyapps.movierecommendersystem.data.repository.NetworkState
import com.urbanbunnyapps.movierecommendersystem.data.vo.MovieDetails
import io.reactivex.disposables.CompositeDisposable

class MovieDetailsViewModel(private val movieRepository: MovieDetailsRepository, movieId: Int): ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val movieDetails: LiveData<MovieDetails> by lazy {
        movieRepository.fetchSingleMovieDetails(compositeDisposable, movieId)
    }

    val networkState: LiveData<NetworkState> by lazy {
        movieRepository.getMovieDetailsNetworkState()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}