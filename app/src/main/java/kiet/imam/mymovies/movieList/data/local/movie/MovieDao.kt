package kiet.imam.mymovies.movieList.data.local.movie

import androidx.room.Dao
import androidx.room.Upsert
import androidx.room.Query


@Dao
interface MovieDao {

    @Upsert
    suspend fun upsertMovieList(movieList: List<MovieEntity>)

    @Query("SELECT * FROM MovieEntity WHERE id = :id")
    suspend fun getMovieById(id : Int):MovieEntity

    @Query("SELECT * FROM MovieEntity WHERE category = :category")
    suspend fun getMovieByCategory(category : String):List<MovieEntity>



}