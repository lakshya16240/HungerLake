package com.example.hungerlake.Repository

import androidx.paging.Pager
import com.example.hungerlake.models.Item
import com.example.hungerlake.models.LatLong
import com.example.hungerlake.models.ReviewEntity
import com.example.hungerlake.models.Venue

interface RestaurantRepository {

    suspend fun getRestaurantList(pageSize : Int, latLong: LatLong) : Pager<Int, Item>

    suspend fun getRestaurantDetails(restaurantId : String) : Venue

    suspend fun getRestaurantRejectPref(id : String) : Boolean

    suspend fun updateRestaurantRejectPref(id : String)

    suspend fun saveRestaurantDetailsToDb(venue: Venue) : Long

    suspend fun saveReviewToDb(reviewEntity: ReviewEntity) : Long
}