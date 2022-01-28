package com.urbanbunnyapps.movierecommendersystem.data.vo


import com.google.gson.annotations.SerializedName

data class MovieDetails(
    val budget: Int,
    val homepage: String,
    val id: Int,
    @SerializedName("imdb_id")
    val imdbId: String,
    @SerializedName("original_language")
    val originalLanguage: String,
    @SerializedName("original_title")
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("release_date")
    val releaseDate: String,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean? = null,
    @SerializedName("vote_average")
    val rating: Double
)