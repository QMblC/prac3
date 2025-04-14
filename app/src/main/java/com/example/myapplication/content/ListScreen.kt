package com.example.myapplication.content

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.example.myapplication.model.MovieShort
import com.example.myapplication.view.ListViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(navigation: NavHostController) {
    val viewModel = koinViewModel<ListViewModel> { parametersOf(navigation) }
    val state = viewModel.viewState

    Scaffold(
        topBar = {
            Row(
                Modifier.padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = state.query,
                    onValueChange = {
                        viewModel.onQueryChanged(it)
                    },
                    label = { Text("Введите название фильма") },
                    modifier = Modifier
                        .width(360.dp)
                        .padding(vertical = 16.dp)
                )
                BadgedBox(
                    badge = { if (state.hasBadge) Badge() },
                    Modifier.padding(3.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.MoreVert,
                        contentDescription = "More",
                        modifier = Modifier
                            .clickable { viewModel.onFiltersClicked() }
                            .size(80.dp)
                    )
                }
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)) {
            when {state.showTypesDialog -> {
                SelectionDialog(
                    onDismissRequest = { viewModel.onSelectionDialogDismissed() },
                    onConfirmation = { viewModel.onFiltersConfirmed() },
                    title = "Тип",
                    variants = state.typesVariants,
                    selectedVariants = state.selectedTypes
                ) { variant, isSelected ->
                    viewModel.onSelectedVariantChanged(variant, isSelected)
                    }
                }
            }

            when {
                state.isLoading -> {
                    FullscreenLoading()
                }

                state.error != null -> {
                    FullscreenMessage(msg = state.error ?: "")
                }

                state.isEmpty -> {
                    FullscreenMessage("По запросу нет результатов")
                }

                else -> {
                    val list = viewModel.viewState.items
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 10.dp, vertical = 5.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(list) { movie ->
                            MovieCard(movie = movie, navigation, onClick = { viewModel.onItemClicked(movie.id) })
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FullscreenLoading() {
    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun FullscreenMessage(msg: String) {
    Box(
        Modifier
            .fillMaxSize()
            .padding(10.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = msg)
    }
}

@Composable
fun MovieCard(movie: MovieShort, navigation: NavHostController, onClick: () -> Unit) {
    val viewModel = koinViewModel<ListViewModel> { parametersOf(navigation) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp).
            pointerInput(Unit) {
                detectTapGestures(
                    onTap = { viewModel.onItemClicked(movie.id) },
                    onDoubleTap = { viewModel.onItemDoubleClicked(movie) }
                )
            },
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column {
                    Text(
                        text = movie.name,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                    Text(
                        text = "Тип: ${movie.type}",
                        color = Color.Gray,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                    Text(
                        text = "Жанр: ${movie.genres.joinToString(", ") { it.displayName }}",
                        color = Color.Gray,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
            }
            AsyncImage(
                model = movie.posterImageURL,
                contentDescription = "Poster",
                modifier = Modifier
                    .height(120.dp)
                    .padding(start = 8.dp)
            )
        }
    }
}
