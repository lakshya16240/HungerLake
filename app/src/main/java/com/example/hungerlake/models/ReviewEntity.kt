package com.example.hungerlake.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "Review")
data class ReviewEntity(
    @PrimaryKey val reviewId : String = UUID.randomUUID().toString(),
    val reviewText : String,
    val username : String,
    val venueId : String
)
