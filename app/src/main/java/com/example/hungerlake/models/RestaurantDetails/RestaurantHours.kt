package com.example.hungerlake.models.RestaurantDetails

import androidx.room.Entity

@Entity
data class RestaurantHours(
    val isOpen: Boolean,
    val status: String,
    val timeframes: List<TimeFrame>
)