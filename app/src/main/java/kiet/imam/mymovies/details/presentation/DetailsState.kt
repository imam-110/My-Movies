package kiet.imam.mymovies.details.presentation

import kiet.imam.mymovies.movieList.domain.Model.Movie

data class DetailsState(
    val isLoading : Boolean = false,
    val movie : Movie? = null
)
