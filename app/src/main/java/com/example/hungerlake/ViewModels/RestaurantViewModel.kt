package com.example.hungerlake.ViewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hungerlake.Repository.RestaurantRepositoryImpl
import com.example.hungerlake.models.LatLong
import com.example.hungerlake.models.ReviewEntity
import com.example.hungerlake.models.Venue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RestaurantViewModel(
        private val restaurantRepository: RestaurantRepositoryImpl
) : ViewModel() {

    private val TAG = "RestaurantViewModel"
    private var restaurantDetails : MutableLiveData<Venue> = MutableLiveData()
    private var reviewsList : MutableLiveData<List<ReviewEntity>> = MutableLiveData()
    private lateinit var _reviewsList : MutableList<ReviewEntity>

    fun getRestaurantDetails() : LiveData<Venue> = restaurantDetails

    fun getReviewsList() : LiveData<List<ReviewEntity>> = reviewsList

    fun setReviewsList(reviewsList: MutableList<ReviewEntity>){
        _reviewsList = reviewsList
        this.reviewsList.postValue(_reviewsList)
    }

    fun fetchRestaurantDetails(restaurantId : String){
        viewModelScope.launch(Dispatchers.IO) {
            val restaurantDetailsRoom = restaurantRepository.getRestaurantDetails(restaurantId)

            restaurantDetails.postValue(restaurantDetailsRoom)
        }
    }


    fun addReview(reviewText: String, venueId: String) {
        val review = ReviewEntity(
            reviewText = reviewText,
            username = "anon",
            venueId = venueId
        )

        _reviewsList.add(review)
        reviewsList.postValue(_reviewsList)

        viewModelScope.launch(Dispatchers.IO) {
            val result = restaurantRepository.saveReviewToDb(review)
            Log.d(TAG, "saveReviewToDb: $result")
        }

    }

    fun updateRestaurantRejectPref(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = restaurantRepository.updateRestaurantRejectPref(id)
            Log.d(TAG, "saveReviewToDb: $result")
        }

    }

}