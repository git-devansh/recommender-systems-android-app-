package com.urbanbunnyapps.movierecommendersystem.recommed.api

import com.urbanbunnyapps.movierecommendersystem.data.vo.MovieDetails
import com.urbanbunnyapps.movierecommendersystem.recommed.MovieTitle
import com.urbanbunnyapps.movierecommendersystem.recommed.MovieTitles
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

// http://mymovierecommender.herokuapp.com/get_titles
const val RECOMMEND_BASE_URL = "http://mymovierecommender.herokuapp.com/"

interface RecommendApi {

    @GET("get_titles")
    suspend fun getMovieTitles(): Response<MovieTitles>


    @GET("get_recommendation/{movie}")
    suspend fun getRecommendation(
        @Path("movie") movie: String): ArrayList<MovieDetails>

}