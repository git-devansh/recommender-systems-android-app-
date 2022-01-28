package com.urbanbunnyapps.movierecommendersystem.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class UserMovieData(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val isFavorite: Boolean
)