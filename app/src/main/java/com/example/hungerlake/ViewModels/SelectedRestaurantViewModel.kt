package com.example.hungerlake.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hungerlake.Mappers.RestaurantDTOMapper
import com.example.hungerlake.models.VenueServiceEntity
import com.example.hungerlake.models.Venue

class SelectedRestaurantViewModel(
        private val restaurantDTOMapper: RestaurantDTOMapper
) : ViewModel() {
    private val selectedRestaurant : MutableLiveData<Venue> = MutableLiveData()

    fun setSelectedRestaurant(venue: VenueServiceEntity) {
        val venueDetailsRoom = restaurantDTOMapper.mapFromServiceEntity(venue)

        selectedRestaurant.value = venueDetailsRoom
    }

    fun getSelectedRestaurant() : LiveData<Venue> = selectedRestaurant
}