package com.example.myapplication.content

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.example.myapplication.model.Movie
import com.example.myapplication.model.MovieAttributes.Rating
import com.example.myapplication.view.DetailsViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun DetailsScreen(navigation: NavHostController, movieId: Int) {
    val viewModel = koinViewModel<DetailsViewModel> { parametersOf(navigation, movieId) }
    MovieScreenContent(
        movie = viewModel.viewState.movie,
        onBackPressed = { viewModel.back() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MovieScreenContent(
    movie: Movie?,
    onBackPressed: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = movie?.name.orEmpty()) },
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = "Назад")
                    }
                }
            )
        }
    ) { paddingValues ->
        if (movie == null) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Фильм не найден", fontSize = 18.sp)
            }
            return@Scaffold
        }

        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    AsyncImage(
                        model = movie.posterImageURL,
                        contentDescription = "Постер",
                        modifier = Modifier
                            .height(300.dp)
                            .width(200.dp)
                            .clip(MaterialTheme.shapes.medium),
                        contentScale = ContentScale.Crop
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1f)
                            .padding(end = 8.dp)
                    ) {
                        Text(
                            text = "Описание:",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                        Text(
                            text = movie.plot,
                            fontSize = 14.sp
                        )
                    }
                }

                RatingDisplay(movie.rating)

                Spacer(modifier = Modifier.height(16.dp))
                movie.genres.forEach { genre ->
                    Text(
                        text = genre.displayName,
                        modifier = Modifier.padding(horizontal = 14.dp)
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                InfoSection("Страны выхода", movie.countries)
                Spacer(modifier = Modifier.height(24.dp))
                Text(text = "Съёмочная команда:", fontWeight = FontWeight.Bold, fontSize = 16.sp)

                LazyRow(
                    modifier = Modifier.padding(top = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(movie.people) { person ->
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            AsyncImage(
                                model = person.photo,
                                contentDescription = "Фото ${person.name}",
                                modifier = Modifier
                                    .size(100.dp)
                                    .clip(MaterialTheme.shapes.small),
                                contentScale = ContentScale.Crop
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = person.name, fontSize = 12.sp)

                        }
                    }
                }
            }
            item {
                Spacer(modifier = Modifier.height(100.dp))
            }
        }
    }
}

@Composable
fun RatingDisplay(rating: Rating) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        RatingText("КП", rating.kp.toFloat())
        RatingText("IMDb", rating.imdb.toFloat())
        RatingText("Критики", rating.filmCritics.toFloat())
    }
}

@Composable
private fun RatingText(label: String, value: Float) {
    Text(
        text = "$label: $value",
        fontSize = 14.sp
    )
}

@Composable
fun InfoSection(title: String, items: List<String>) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
        items.forEach { item ->
            Text(
                text = item,
                modifier = Modifier.padding(start = 8.dp, top = 2.dp),
                fontSize = 14.sp
            )
        }
    }
}
