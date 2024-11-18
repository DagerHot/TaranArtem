package com.example.lesson_8
import retrofit2.http.GET
import retrofit2.http.Query
import com.squareup.moshi.Json

interface ApiService {
    @GET("path_to_upcoming_anime")
    suspend fun getUpcoming(): AnimeResponse

    @GET("path_to_top_anime")
    suspend fun getTopAnime(): AnimeResponse

    @GET("path_to_top_ova")
    suspend fun getTopOva(): AnimeResponse
}

data class AnimeResponse(
    val data: List<Anime>
)
