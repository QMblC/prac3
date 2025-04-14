package com.example.myapplication.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.myapplication.model.MovieType

@Composable
fun SelectionDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    title: String,
    variants: Set<MovieType>,
    selectedVariants: Set<MovieType>,
    onVariantSelectedChanged: (MovieType, Boolean) -> Unit
) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            shape = RoundedCornerShape(10.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = title,
                    modifier = Modifier.padding(10.dp),
                )

                variants.forEach { variant ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = variant in selectedVariants,
                            onCheckedChange = { onVariantSelectedChanged(variant, it) },
                        )
                        Text(text = variant.displayName)
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                ) {
                    TextButton(
                        onClick = { onDismissRequest() },
                        modifier = Modifier.padding(10.dp),
                    ) {
                        Text("Отмена")
                    }
                    TextButton(
                        onClick = { onConfirmation() },
                        modifier = Modifier.padding(10.dp),
                    ) {
                        Text("Подтвердить")
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun SelectionDialogPreview() {
    SelectionDialog(
        onDismissRequest = {},
        onConfirmation = {},
        title = "Тип",
        variants = setOf(
            MovieType.MOVIE,
            MovieType.TV_SERIES,
            MovieType.CARTOON,
            MovieType.ANIME
        ),
        selectedVariants = setOf(MovieType.TV_SERIES),
        onVariantSelectedChanged = { _, _ -> }
    )
}