package com.example.hungerlake.models

import androidx.room.Embedded
import androidx.room.Relation

data class VenueDTO(
    @Embedded
    val venue: Venue,
    @Relation(
        parentColumn = "id",
        entityColumn = "venueId"
    )
    val reviews : List<ReviewEntity>
)
