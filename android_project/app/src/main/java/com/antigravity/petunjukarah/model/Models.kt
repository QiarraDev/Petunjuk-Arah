package com.antigravity.petunjukarah.model

import com.google.gson.annotations.SerializedName

data class Destination(
    val id: Long? = null,
    val name: String,
    val description: String,
    val category: String,
    val location: String,
    val rating: Double,
    val price: String, // Changed from Double to String to match backend
    val imageUrl: String? = null,
    val lat: Double? = null,
    val lon: Double? = null
)

data class Note(
    val id: Long? = null,
    val title: String,
    val content: String,
    val createdAt: String? = null,
    val alarmTime: String? = null
)

data class Trip(
    val id: Long? = null,
    val name: String,
    @SerializedName("mainDestination") val destination: String,
    val startDate: String,
    val duration: Int? = null,
    val reminderTime: String? = null
)
