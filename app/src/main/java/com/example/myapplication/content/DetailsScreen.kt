package com.example.myapplication.content

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
import com.example.myapplication.view.DetailsViewModel
import com.example.myapplication.view.MovieDetailsUI
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

object PaddingDefaults {
    val small = Modifier.padding(4.dp)
    val medium = Modifier.padding(8.dp)
    val large = Modifier.padding(16.dp)
}

@Composable
fun DetailsScreen(navController: NavHostController, movieId: Int) {
    val viewModel = koinViewModel<DetailsViewModel> { parametersOf(navController, movieId) }
    viewModel.uiState.value?.let { movie ->
        MovieDetails(movie, onBackPressed = { viewModel.back() })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MovieDetails(
    movie: MovieDetailsUI,
    onBackPressed: () -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = movie.name) },
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
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .then(PaddingDefaults.large),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                AsyncImage(
                    model = movie.posterImageURL,
                    contentDescription = "Poster ${movie.name}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp),
                    contentScale = ContentScale.Crop
                )
            }

            item {
                Text(
                    text = movie.plot,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = PaddingDefaults.medium
                )
            }

            item {
                EntityDetailsSection(
                    title = "Movie Details",
                    attributes = movie.details
                )
            }

            item {
                Text(
                    text = "Cast:",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = PaddingDefaults.medium
                )
            }

            items(movie.cast) { person ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        model = person.photoURL,
                        contentDescription = "Photo ${person.name}",
                        modifier = Modifier
                            .size(80.dp)
                            .then(PaddingDefaults.small),
                        contentScale = ContentScale.Crop
                    )
                    Column {
                        Text(text = person.name, fontWeight = FontWeight.Bold)
                        Text(text = "Role: ${person.role}")
                    }
                }

            }
            item {
                Spacer(modifier = Modifier.height(90.dp))
            }
        }
    }
}

@Composable
fun EntityDetailsSection(
    title: String,
    attributes: List<Pair<String, String>>
) {
    Column(modifier = PaddingDefaults.large) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = PaddingDefaults.medium
        )

        attributes.forEach { (label, value) ->
            Text(
                text = "$label: $value",
                style = MaterialTheme.typography.bodyMedium,
                modifier = PaddingDefaults.small
            )
        }
    }
}


