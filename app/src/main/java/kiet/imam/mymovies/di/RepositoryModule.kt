package kiet.imam.mymovies.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kiet.imam.mymovies.movieList.data.repository.MovieListRepositoryImpl
import kiet.imam.mymovies.movieList.domain.repository.MovieListRepository
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
 abstract class RepositoryModule {

  @Binds
  @Singleton
  abstract fun bindMovieListRepository(
   movieListRepositoryImpl: MovieListRepositoryImpl
  ): MovieListRepository




}