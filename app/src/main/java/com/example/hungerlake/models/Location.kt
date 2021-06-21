package com.example.hungerlake.models


import com.google.gson.annotations.SerializedName

data class Location(
    val distance: Int,
    val formattedAddress: List<String>
)