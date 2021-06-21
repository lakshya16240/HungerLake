package com.example.hungerlake.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.example.hungerlake.Repository.RestaurantRepositoryImpl
import com.example.hungerlake.models.Item
import com.example.hungerlake.models.LatLong
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map

class RestaurantListViewModel(
        private val restaurantRepository: RestaurantRepositoryImpl
) : ViewModel() {

    private val TAG = "RESTAURANT LIST VIEWMODEL"
    private var latLong : LatLong = LatLong()

    fun setLatLong(latLong: LatLong){
        this.latLong = latLong
    }

    suspend fun getRestaurantListing() : Flow<PagingData<Item>> {
        val pagingDataFlow = restaurantRepository.getRestaurantList(20,latLong)
            .flow
            .map {
                it.filter { !restaurantRepository.getRestaurantRejectPref(it.venue.id) }
            }
            .cachedIn(viewModelScope)
        return pagingDataFlow
    }
    
}