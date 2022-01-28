package com.urbanbunnyapps.movierecommendersystem.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserMovieDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(userMovieData: UserMovieData)

    @Query("SELECT * FROM user_table WHERE id=:movieID")
    fun getUserMovieData(movieID: Int): LiveData<UserMovieData>

}