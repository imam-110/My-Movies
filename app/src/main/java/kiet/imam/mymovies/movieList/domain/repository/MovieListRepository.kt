package kiet.imam.mymovies.movieList.domain.repository

import kiet.imam.mymovies.movieList.domain.Model.Movie
import kiet.imam.mymovies.movieList.utils.Resource
import kotlinx.coroutines.flow.Flow

interface MovieListRepository {

    suspend fun getMovieList(
        forceFetchFromRemote : Boolean,
        category : String,
        page : Int
    ) : Flow<Resource<List<Movie>>>


    suspend fun getMovie(
        id : Int
    ) : Flow<Resource<Movie>>
}