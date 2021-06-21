package com.example.hungerlake.Repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.hungerlake.DAOs.RestaurantDao
import com.example.hungerlake.Mappers.RestaurantDTOMapper
import com.example.hungerlake.Services.RestaurantItemPagingSourceService
import com.example.hungerlake.Services.FourSquareApiService
import com.example.hungerlake.models.*

class RestaurantRepositoryImpl(
        private val fourSquareApiService: FourSquareApiService,
        private val restaurantDTOMapper: RestaurantDTOMapper,
        private val restaurantDao : RestaurantDao
) : RestaurantRepository {


    private val TAG = "RestaurantRepositoryImpl"
    override suspend fun getRestaurantList(pageSize: Int, latLong: LatLong): Pager<Int, Item> {
        return Pager(
                config = PagingConfig(20,maxSize = 60),
                pagingSourceFactory = {RestaurantItemPagingSourceService(fourSquareApiService,latLong)}
        )
    }

    override suspend fun getRestaurantDetails(restaurantId: String) : Venue {
        val venueDTO : VenueDTO? = restaurantDao.getRestaurantById(restaurantId)
        Log.d(TAG, "getRestaurantDetails: $restaurantId")
        return if(venueDTO != null){
            Log.d(TAG , "getRestaurantDetails $venueDTO")
            restaurantDTOMapper.mapFromVenueDTO(venueDTO)
        } else{
            Log.d(TAG, "getRestaurantDetails: Service" )
            val venue = restaurantDTOMapper.mapFromServiceEntity(fourSquareApiService.getRestaurantDetails(restaurantId).response.venue)
            saveRestaurantDetailsToDb(venue)
            venue
        }
    }

    override suspend fun getRestaurantRejectPref(id: String): Boolean {
        val restaurantRejectPref = restaurantDao.getRestaurantRejectPref(id) ?: false
        Log.d(TAG, "getRestaurantRejectPref: $restaurantRejectPref")
        return restaurantRejectPref
    }

    override suspend fun updateRestaurantRejectPref(id: String) {
        restaurantDao.updateRestaurantRejectPref(id)
    }

    override suspend fun saveRestaurantDetailsToDb(venue: Venue): Long {
        return restaurantDao.insertOrUpdateRestaurant(venue)
    }

    override suspend fun saveReviewToDb(reviewEntity: ReviewEntity): Long {
        return restaurantDao.insertOrUpdateReview(reviewEntity)
    }


}