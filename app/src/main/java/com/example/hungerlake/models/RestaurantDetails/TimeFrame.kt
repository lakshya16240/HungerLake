package com.example.hungerlake.models.RestaurantDetails


import com.google.gson.annotations.SerializedName

data class TimeFrame(
    val days: String,
    val includesToday: Boolean,
    val `open`: List<Open>
)