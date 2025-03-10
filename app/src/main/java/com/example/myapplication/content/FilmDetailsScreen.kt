package com.example.myapplication.content

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.example.myapplication.model.Movie
import com.example.myapplication.model.Person
import com.example.myapplication.view.DetailsViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun FilmDetailsScreen(navController: NavHostController, movieId: Int) {
    val viewModel = koinViewModel<DetailsViewModel> { parametersOf(navController, movieId) }

    MovieDetails(
        movie = viewModel.mutableState.movie,
        onBackPressed = { viewModel.back() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MovieDetails(
    movie: Movie?,
    onBackPressed: () -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { movie?.name?.let { Text(text = it) } },
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(
                            Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = "Назад"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        movie?.let {
            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .padding(top = 16.dp, bottom = 116.dp, start = 16.dp, end = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    AsyncImage(
                        model = it.posterImageURL,
                        contentDescription = "Poster ${it.name}",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(400.dp),
                        contentScale = ContentScale.Crop
                    )
                }

                item {
                    Text(
                        text = it.plot,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                item {
                    Column {
                        Text(
                            text = "Premier year: ${it.premierYear}",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Rating: ${it.rating.aggregateRating}",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Genres: ${it.genres.joinToString(", ")}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = "Countries: ${it.countries.joinToString(", ")}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }

                item {
                    Text(
                        text = "Cast:",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
                items(it.people) { person ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AsyncImage(
                            model = person.photoURL,
                            contentDescription = "Photo ${person.name}",
                            modifier = Modifier
                                .size(80.dp)
                                .padding(end = 8.dp),
                            contentScale = ContentScale.Crop
                        )
                        Column {
                            Text(text = person.name, fontWeight = FontWeight.Bold)
                            Text(text = "Role: ${person.characters.joinToString(", ")}")
                        }
                    }
                }
            }
        }
    }
}
