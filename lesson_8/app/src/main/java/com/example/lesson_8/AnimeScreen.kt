package com.example.lesson_8

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items // Важливо
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import androidx.compose.foundation.shape.RoundedCornerShape

@Composable
fun AnimeScreen() {
    var animeList by remember { mutableStateOf<List<Anime>>(emptyList()) }
    var currentPage by remember { mutableStateOf(0) }

    // Викликаємо API асинхронно
    LaunchedEffect(currentPage) {
        animeList = when (currentPage) {
            0 -> RetrofitInstance.api.getUpcoming().data.take(6) // Повертає List<Anime>
            1 -> RetrofitInstance.api.getTopAnime().data.take(6) // Повертає List<Anime>
            else -> RetrofitInstance.api.getTopOva().data.take(6) // Повертає List<Anime>
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp) // Опускаємо елементи від top bar
    ) {
        if (animeList.isEmpty()) {
            Text(
                text = "Завантаження...",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 16.dp) // Додаємо відступ сітці
            ) {
                items(animeList) { anime ->
                    AnimeItem(anime)
                }
            }
        }

        // Кнопка в центрі
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = androidx.compose.ui.Alignment.Center
        ) {
            Button(
                onClick = {
                    currentPage = (currentPage + 1) % 3
                },
                modifier = Modifier
                    .height(60.dp) // Встановлюємо висоту
                    .width(200.dp) // Встановлюємо ширину
            ) {
                Text(
                    text = when (currentPage) {
                        0 -> "Невдовзі"
                        1 -> "Топ Аніме"
                        else -> "Топ Аніме OVA"
                    },
                    fontSize = androidx.compose.ui.unit.TextUnit.Unspecified, // Можна змінити розмір тексту, якщо потрібно
                )
            }
        }
    }
}

@Composable
fun AnimeItem(anime: Anime) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .background(Color.Gray, shape = RoundedCornerShape(8.dp))
            .aspectRatio(1f)
    ) {
        Image(
            painter = rememberImagePainter(anime.images.jpg.image_url),
            contentDescription = anime.title,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}
