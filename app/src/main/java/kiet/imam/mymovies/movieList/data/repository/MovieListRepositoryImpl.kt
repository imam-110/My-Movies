package kiet.imam.mymovies.movieList.data.repository

import kiet.imam.mymovies.movieList.data.local.movie.MovieDatabase
import kiet.imam.mymovies.movieList.data.mappers.toMovie
import kiet.imam.mymovies.movieList.data.mappers.toMovieEntity
import kiet.imam.mymovies.movieList.data.remotes.MovieApi
import kiet.imam.mymovies.movieList.domain.Model.Movie
import kiet.imam.mymovies.movieList.domain.repository.MovieListRepository
import kiet.imam.mymovies.movieList.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class MovieListRepositoryImpl @Inject constructor (
    private val movieApi: MovieApi,
    private val movieDatabase: MovieDatabase
) : MovieListRepository {
    override suspend fun getMovieList(
        forceFetchFromRemote: Boolean,
        category: String,
        page: Int
    ): Flow<Resource<List<Movie>>> {
        return flow {
            emit(Resource.Loading(true))
            val localMovieList = movieDatabase.movieDao.getMovieByCategory(category)

            val shouldLoadLocalMovies = localMovieList.isNotEmpty() && !forceFetchFromRemote


            if (shouldLoadLocalMovies) {
                emit(Resource.Success(
                  data = localMovieList.map { movieEntity ->
                      movieEntity.toMovie(category)
                  }
                ))
                emit(Resource.Loading(false))
                return@flow
            }
            val movieListFromApi = try{
                movieApi.getMoviesList(category,page)
            }catch(e : IOException){
                e.printStackTrace()
                emit(Resource.Error(message = "Error Loading Movies"))
                return@flow
            }catch(e : HttpException){
                e.printStackTrace()
                emit(Resource.Error(message = "Error Loading Movies"))
                return@flow
            }catch(e :Exception){
                e.printStackTrace()
                emit(Resource.Error(message = "Error Loading Movies"))
                return@flow
            }

            val movieEntities = movieListFromApi.results.let {
                it.map { movieDto ->
                    movieDto.toMovieEntity(category)
                }
            }
            movieDatabase.movieDao.upsertMovieList(movieEntities)

            emit(Resource.Success(
                movieEntities.map { it.toMovie(category) }
            ))
            emit(Resource.Loading(false))

        }
    }

    override suspend fun getMovie(id: Int): Flow<Resource<Movie>> {
       return flow {

           emit(Resource.Loading(true))
           val movieEntity = movieDatabase.movieDao.getMovieById(id)

           if(movieEntity != null) {
               emit(
                   Resource.Success(movieEntity.toMovie(movieEntity.category))
               )

               emit(Resource.Loading(false))
               return@flow
           }
           emit(Resource.Error("Error No Such Movie"))
           emit(Resource.Loading(false))

       }
    }
}