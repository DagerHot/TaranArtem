package com.example.lesson_8

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview


data class Anime(
    val title: String,
    val images: AnimeImages
)

data class AnimeImages(
    val jpg: AnimeImageUrls
)

data class AnimeImageUrls(
    val image_url: String
)

@Preview(showBackground = true)
@Composable
fun PreviewAnimeScreen() {
    AnimeScreen()
}
