package com.example.myapplication.view

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.myapplication.data.MovieRepository
import com.example.myapplication.model.Movie

class DetailsViewModel(
    private val repository: MovieRepository,
    private val navigation: NavHostController,
    private val id: Int
) : ViewModel() {

    val uiState = mutableStateOf<MovieDetailsUI?>(null)

    init {
        repository.getMovie(id)?.let {
            uiState.value = MovieDetailsUI.from(it)
        }
    }

    fun back() {
        navigation.popBackStack()
    }
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
                    "Rating" to movie.rating.aggregateRating.toString(),
                    "Genres" to movie.genres.joinToString(", "),
                    "Countries" to movie.countries.joinToString(", ")
                ),
                cast = movie.people.map { PersonUI(it.name, it.characters.joinToString(", "), it.photoURL) }
            )
        }
    }
}

data class PersonUI(
    val name: String,
    val role: String,
    val photoURL: String
)
