package com.example.hungerlake.Mappers

import androidx.room.TypeConverter
import com.example.hungerlake.models.RestaurantDetails.RestaurantHours
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RestaurantDataConverter {

    @TypeConverter
    fun convertToJson(restaurantHours: RestaurantHours): String? {

        val gson = Gson()
        return gson.toJson(restaurantHours)
    }

    fun convertJsonToObject(jsonString : String) : RestaurantHours{
        val objectType = object : TypeToken<RestaurantHours>(){}.type
        return Gson().fromJson(jsonString,objectType)
    }
}