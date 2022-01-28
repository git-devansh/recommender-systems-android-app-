package com.urbanbunnyapps.movierecommendersystem.recommed.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    val api: RecommendApi by lazy {
        Retrofit.Builder()
            .baseUrl(RECOMMEND_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RecommendApi::class.java)
    }

}