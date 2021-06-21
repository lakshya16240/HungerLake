package com.example.hungerlake.models


import androidx.room.*
import com.example.hungerlake.models.RestaurantDetails.*
import com.google.gson.annotations.SerializedName

data class VenueServiceEntity(
    val categories: List<Category>,
    val contact: Contact,
    val id: String,
    val location: Location,
    val name: String,
    val verified: Boolean,
    val bestPhoto: BestPhoto?,
    var rating : Double?,
    var hours : RestaurantHours?,
    var popular : RestaurantHours?,
    var attributes : RestaurantAttributes?,
    var thumbsUp : Boolean?,
    var thumbsDown : Boolean?,
    var tips: RestaurantTip?,
    var menu : Menu?,
    var url : String?

)