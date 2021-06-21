package com.example.hungerlake.models


data class Response(
    val groups: List<RestaurantGroup>,
    val headerFullLocation: String,
    val totalResults: Int
)