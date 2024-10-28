package com.example.petfinderdemo

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

val retrofit = Retrofit.Builder().baseUrl("https://sniper450.github.io/")
    .addConverterFactory(GsonConverterFactory.create()).build()

val locationService = retrofit.create(ApiService::class.java)

interface ApiService{
    @GET("dogFinder/by_location.json")
    suspend fun getLocations() : Map<String, List<String>>
}

val retrofit1 = Retrofit.Builder().baseUrl("https://dog.ceo/api/")
    .addConverterFactory(GsonConverterFactory.create()).build()

val imageService = retrofit1.create(ApiImageService::class.java)

interface ApiImageService{
    @GET("breed/{breed}/images/random/1")
    suspend fun getImages(@Path("breed") breed: String) : ImageResponse
}
