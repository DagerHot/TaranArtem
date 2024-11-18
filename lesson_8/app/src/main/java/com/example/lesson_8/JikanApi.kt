package com.example.lesson_8

import retrofit2.http.GET
import retrofit2.http.Query
import com.squareup.moshi.Json // або імпорт для Gson, залежно від вашого вибору



interface JikanApi {
    @GET("seasons/upcoming")
    suspend fun getUpcoming(): AnimeResponse

    @GET("top/anime?sfw")
    suspend fun getTopAnime(): AnimeResponse

    @GET("top/anime?type=ova")

 suspend fun getTopOva(): AnimeResponse
}