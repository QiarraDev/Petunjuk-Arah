package com.antigravity.petunjukarah.network

import com.antigravity.petunjukarah.model.Destination
import com.antigravity.petunjukarah.model.Note
import com.antigravity.petunjukarah.model.Trip
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/api/destinations")
    suspend fun getDestinations(): List<Destination>

    @GET("/api/destinations/search")
    suspend fun searchDestinations(@Query("query") query: String): List<Destination>

    @GET("/api/notes")
    suspend fun getNotes(): List<Note>

    @GET("/api/trips")
    suspend fun getTrips(): List<Trip>

    @retrofit2.http.PUT("/api/notes/{id}")
    suspend fun updateNote(@retrofit2.http.Path("id") id: Long, @retrofit2.http.Body note: Note): Note
}

object RetrofitClient {
    private const val BASE_URL = "http://10.0.2.2:8080" // Localhost from Emulator

    val instance: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
