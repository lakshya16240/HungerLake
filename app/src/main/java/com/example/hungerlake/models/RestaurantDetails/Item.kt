package com.example.hungerlake.models.RestaurantDetails


import com.google.gson.annotations.SerializedName

data class Item(
    val displayName: String,
    val displayValue: String,
    val priceTier: Int
)