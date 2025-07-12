package com.example.animebox.ui.anime

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.foundation.clickable
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Surface
import androidx.compose.ui.window.Dialog
import com.example.animebox.domain.model.AnimeDetailsItem

@Composable
fun AnimeScreen(viewModel: AnimeViewModel) {
    val animeList by viewModel.animeList.collectAsState()
    val animeDetails by viewModel.animeDetails.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val (query, setQuery) = remember { mutableStateOf("") }
    val (selectedAnimeId, setSelectedAnimeId) = remember { mutableStateOf<Int?>(null) }

    if (selectedAnimeId != null && animeDetails != null) {
        AnimeDetailsDialog(
            details = animeDetails!!,
            onDismiss = {
                setSelectedAnimeId(null)
                viewModel.clearAnimeDetails()
            }
        )
    }

    if (errorMessage != null) {
        AlertDialog(
            onDismissRequest = { viewModel.clearError() },
            title = { Text("Ошибка") },
            text = { Text(errorMessage ?: "") },
            confirmButton = {
                Button(onClick = { viewModel.clearError() }) {
                    Text("ОК")
                }
            }
        )
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = query,
                onValueChange = setQuery,
                label = { Text("Название аниме") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = { viewModel.loadAnime(query) }) {
                Text("Поиск")
            }
        }
        Box(modifier = Modifier.fillMaxSize()) {
            when {
                isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                animeList.isEmpty() -> {
                    Text("Нет результатов", modifier = Modifier.align(Alignment.Center))
                }
                else -> {
                    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                        items(animeList) { anime ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp)
                                    .clickable {
                                        setSelectedAnimeId(anime.id)
                                        viewModel.loadAnimeDetails(anime.id)
                                    },
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    painter = rememberAsyncImagePainter(anime.imageUrl),
                                    contentDescription = anime.title,
                                    modifier = Modifier.size(80.dp),
                                    contentScale = ContentScale.Crop
                                )
                                Spacer(modifier = Modifier.width(16.dp))
                                Text(text = anime.title)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AnimeDetailsDialog(details: AnimeDetailsItem, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(details.title ?: "Без названия") },
        text = {
            Column {
                details.imageUrl?.let {
                    Image(
                        painter = rememberAsyncImagePainter(it),
                        contentDescription = details.title ?: "Без названия",
                        modifier = Modifier.size(160.dp),
                        contentScale = ContentScale.Crop
                    )
                } ?: Spacer(modifier = Modifier.height(0.dp))
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = details.synopsis ?: "Описание отсутствует")
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Оценка: ${details.score?.toString() ?: "-"}")
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Жанры: ${details.genres?.joinToString { it.name } ?: "-"}")
            }
        },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text("Закрыть")
            }
        }
    )
} 