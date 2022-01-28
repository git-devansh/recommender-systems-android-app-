package com.urbanbunnyapps.movierecommendersystem.recommed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.urbanbunnyapps.movierecommendersystem.data.vo.MovieDetails
import com.urbanbunnyapps.movierecommendersystem.recommed.api.RecommendApi
import com.urbanbunnyapps.movierecommendersystem.recommed.api.RetrofitInstance
import retrofit2.Response

class RecommendRepository(private val recommendApi: RecommendApi) {

    private val titlesLiveData = MutableLiveData<Response<MovieTitles>>()

    val movieTitle: LiveData<Response<MovieTitles>>
    get() = titlesLiveData

    suspend fun getMovieTitles(){
        val result = recommendApi.getMovieTitles()
        titlesLiveData.postValue(result)
    }

    suspend fun getRecommendMovie(movie: String): ArrayList<MovieDetails>{
        return RetrofitInstance.api.getRecommendation(movie)
    }
}