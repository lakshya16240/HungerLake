package com.example.hungerlake.models.RestaurantDetails


data class TipsGroup(
    val count: Int,
    val items: List<TipsItem>,
    val name: String,
    val type: String
)