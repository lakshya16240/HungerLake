package com.example.hungerlake.DAOs

import androidx.room.*
import com.example.hungerlake.models.ReviewEntity
import com.example.hungerlake.models.Venue
import com.example.hungerlake.models.VenueDTO

@Dao
interface RestaurantDao {

    @Transaction
    @Query("SELECT * from Restaurant WHERE id = :id")
    suspend fun getRestaurantById(id : String) : VenueDTO?

    @Query("SELECT isRejected FROM Restaurant WHERE id = :id")
    suspend fun getRestaurantRejectPref(id : String) : Boolean?

    @Query("UPDATE Restaurant SET isRejected = 1 WHERE id = :id")
    suspend fun updateRestaurantRejectPref(id : String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateRestaurant(venue: Venue) : Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateReview(reviewEntity: ReviewEntity) : Long
}