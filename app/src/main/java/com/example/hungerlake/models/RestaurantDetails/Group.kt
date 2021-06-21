package com.example.hungerlake.models.RestaurantDetails


import com.google.gson.annotations.SerializedName

data class Group(
    val count: Int,
    val items: List<Item>,
    val name: String,
    val summary: String,
    val type: String
)