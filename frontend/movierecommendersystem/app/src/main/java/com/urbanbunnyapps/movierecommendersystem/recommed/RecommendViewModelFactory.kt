package com.urbanbunnyapps.movierecommendersystem.recommed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class RecommendViewModelFactory(private val recommendRepository: RecommendRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RecommedViewModel(recommendRepository) as T
    }

}