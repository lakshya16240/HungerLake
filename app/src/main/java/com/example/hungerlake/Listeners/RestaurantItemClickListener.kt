package com.example.hungerlake.Listeners

import android.view.View
import com.example.hungerlake.models.VenueServiceEntity

interface RestaurantItemClickListener{
    fun onClick(view : View, venue : VenueServiceEntity)
}