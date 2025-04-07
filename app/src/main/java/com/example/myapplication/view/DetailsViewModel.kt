package com.example.myapplication.view

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.myapplication.model.Movie
import com.example.myapplication.model.MovieShort
import com.example.myapplication.data.MoviesRepository
import com.example.myapplication.state.MovieDetailState
import com.example.myapplication.utilities.launchLoadingAndError
import kotlinx.coroutines.launch


class DetailsViewModel(
    private val repository: MoviesRepository,
    private val navigation: NavHostController,
    private val id: Int
) : ViewModel() {

    val uiState = mutableStateOf<MovieDetailsUI?>(null)
    val viewState = MutableDetailsState()

    init {
        viewModelScope.launchLoadingAndError(
            handleError = { viewState.error = it.localizedMessage },
            updateLoading = { viewState.isLoading = it }
        ) {
            Log.e("DEBUG", "$id")
            viewState.movie = repository.getById(id)
            viewState.movie?.name?.let {
                launch { viewState.related = repository.getList(it).take(3) }
            }
        }
    }

    fun back() {
        navigation.popBackStack()
    }
}

class MutableDetailsState : MovieDetailState {
    override var movie: Movie? by mutableStateOf(null)
    override var rating: Float by mutableFloatStateOf(0f)
    override val isRatingVisible: Boolean get() = rating != 0f
    override var isLoading: Boolean by mutableStateOf(false)
    override var error: String? by mutableStateOf(null)
    override var related: List<MovieShort> by mutableStateOf(emptyList())
}

data class MovieDetailsUI(
    val name: String,
    val plot: String,
    val posterImageURL: String,
    val details: List<Pair<String, String>>,
    val cast: List<PersonUI>
) {
    companion object {
        fun from(movie: Movie): MovieDetailsUI {
            return MovieDetailsUI(
                name = movie.name,
                plot = movie.plot,
                posterImageURL = movie.posterImageURL,
                details = listOf(
                    "Premier year" to movie.premierYear.toString(),
                    "Rating" to movie.rating.imdb.toString(),
                    "Genres" to movie.genres.joinToString(", "),
                    "Countries" to movie.countries.joinToString(", ")
                ),
                cast = movie.people.map { PersonUI(it.name, it.profession, it.photo) }
            )
        }
    }
}

data class PersonUI(
    val name: String,
    val role: String,
    val photoURL: String
)
