package com.example.hungerlake.models

import androidx.room.*
import com.example.hungerlake.Mappers.RestaurantDataConverter
import com.example.hungerlake.models.RestaurantDetails.*

@Entity(tableName = "Restaurant")
data class Venue (

        @PrimaryKey(autoGenerate = false)
        val id: String,
        val name: String,
        val type : String,
        val distance : Int,
        val address : String,
        val verified: Boolean,
        val photoUrl : String?,
        val primaryCategory : String?,
        var rating : Double?,
        var isOpen : Boolean?,
        var pricing : String?,
        var thumbsUp : Boolean?,
        var thumbsDown : Boolean?,
        var bestTip: String?,
        var isRejected : Boolean?,
        var menuUrl : String,
        var url : String

){
        @Ignore
        var reviews : MutableList<ReviewEntity> = mutableListOf()
}