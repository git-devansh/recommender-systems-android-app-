package com.urbanbunnyapps.movierecommendersystem.recommed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.urbanbunnyapps.movierecommendersystem.data.vo.MovieDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class RecommedViewModel(private val recommendRepository: RecommendRepository): ViewModel() {

    var recommendMoviesVar: MutableLiveData<ArrayList<MovieDetails>> = MutableLiveData()

    init {
        viewModelScope.launch {
            recommendRepository.getMovieTitles()
        }
    }

    val titles: LiveData<Response<MovieTitles>>
    get() = recommendRepository.movieTitle

    fun getRecommendMovies(movie: String){
        viewModelScope.launch {
            val response = recommendRepository.getRecommendMovie(movie)
            recommendMoviesVar.value = response
        }
    }
}