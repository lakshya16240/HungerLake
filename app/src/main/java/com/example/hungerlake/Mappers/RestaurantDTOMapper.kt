package com.example.hungerlake.Mappers

import com.example.hungerlake.models.ReviewEntity
import com.example.hungerlake.models.VenueServiceEntity
import com.example.hungerlake.models.Venue
import com.example.hungerlake.models.VenueDTO

class RestaurantDTOMapper {

    fun mapFromServiceEntity(venue : VenueServiceEntity) : Venue{
        return Venue(
            venue.id,
            venue.name,
            venue.categories[0].shortName,
            venue.location.distance,
            venue.location.formattedAddress[0],
            venue.verified,
            "${venue.bestPhoto?.prefix}${"200x100"}${venue.bestPhoto?.suffix}",
            venue.categories.find { it.primary }?.shortName,
            venue.rating,
            venue.hours?.isOpen,
            venue.attributes?.groups?.find { it.type == "price" }?.summary,
            venue.thumbsUp,
            venue.thumbsDown,
            venue.tips?.groups?.get(0)?.items?.get(0)?.text,
            false,
            when {
                venue.menu != null -> venue.menu?.mobileUrl!!
                venue.url != null -> venue.url!!
                else -> "https://foursquare.com/"
            },
            when {
                venue.url != null -> venue.url!!
                else -> "https://foursquare.com/"
            }
        )
    }

    fun mapFromVenueDTO(venueDTO: VenueDTO) : Venue{
        val venue = venueDTO.venue
        venue.reviews = venueDTO.reviews as MutableList<ReviewEntity>
        return venue
    }
}