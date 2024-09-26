package kiet.imam.mymovies.details.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kiet.imam.mymovies.movieList.domain.repository.MovieListRepository
import kiet.imam.mymovies.movieList.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DetailsViewModel @Inject constructor (
    private val movieListRepository: MovieListRepository,
    private val savedStateHandle : SavedStateHandle
): ViewModel() {
    private val movieId = savedStateHandle.get<Int>("movieId")

    private var _detailsState = MutableStateFlow(DetailsState())
    val detailsState = _detailsState.asStateFlow()

    init {
        getMovie(movieId?:-1)
    }

    private fun getMovie(id : Int){
        viewModelScope.launch {
            _detailsState.update {
               it.copy(isLoading = true)
            }
            movieListRepository.getMovie(id).collectLatest { result->
                when(result){
                    is Resource.Error -> {
                        _detailsState.update {
                            it.copy(isLoading = true)
                        }
                    }
                    is Resource.Loading -> {
                        _detailsState.update {
                            it.copy(isLoading = result.isLoading)
                        }
                    }
                    is Resource.Success -> {
                        result.data?.let { movie->
                            _detailsState.update {
                                it.copy(movie = movie)
                            }
                        }
                    }
                }
            }
        }
    }

}