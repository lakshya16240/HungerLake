package com.example.hungerlake.models.RestaurantDetails


data class RestaurantTip(
    val count: Int,
    val groups: List<TipsGroup>
)