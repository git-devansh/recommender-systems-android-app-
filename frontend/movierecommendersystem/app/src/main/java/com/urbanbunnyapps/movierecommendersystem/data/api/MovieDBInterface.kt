package com.urbanbunnyapps.movierecommendersystem.data.api

import com.urbanbunnyapps.movierecommendersystem.data.vo.MovieDetails
import com.urbanbunnyapps.movierecommendersystem.data.vo.MovieResponse
import com.urbanbunnyapps.movierecommendersystem.recommed.MovieTitle
import io.reactivex.Single
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url
import okhttp3.ResponseBody




// https://api.themoviedb.org/3/movie/335983?api_key=18ba36a47e17d0f327912bc098a1bb60
// https://api.themoviedb.org/3/movie/popular?api_key=18ba36a47e17d0f327912bc098a1bb60&language=en-US

interface MovieDBInterface {

    @GET("movie/popular")
    fun getPopularMovie(@Query("page") page: Int): Single<MovieResponse>

    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") id: Int) : Single<MovieDetails>


}