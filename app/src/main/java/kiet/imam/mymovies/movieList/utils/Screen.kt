package kiet.imam.mymovies.movieList.utils

sealed class Screen(val rout: String) {
    object Home : Screen("main")
    object PopularMovieList : Screen("popularMovie")
    object UpcomingMovieList : Screen("upcomingMovie")
    object Details : Screen("details")
}