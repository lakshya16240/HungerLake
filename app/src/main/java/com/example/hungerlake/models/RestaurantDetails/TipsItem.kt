package com.example.hungerlake.models.RestaurantDetails


import com.google.gson.annotations.SerializedName

data class TipsItem(
    val agreeCount: Int,
    val canonicalUrl: String,
    val createdAt: Int,
    val disagreeCount: Int,
    val id: String,
    val lang: String,
    val logView: Boolean,
    val text: String,
    val type: String,
)