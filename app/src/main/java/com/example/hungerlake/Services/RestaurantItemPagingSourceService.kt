package com.example.hungerlake.Services

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.hungerlake.ViewModels.RestaurantViewModel
import com.example.hungerlake.models.Item
import com.example.hungerlake.models.LatLong
import org.koin.core.KoinApplication.Companion.init
import java.lang.Exception
import java.util.stream.Collectors

class RestaurantItemPagingSourceService(
    private val fourSquareApiService: FourSquareApiService,
    private val latLong: LatLong
) : PagingSource<Int, Item>(){


    private val TAG = "RestaurantItemPagingSourceService"
    override fun getRefreshKey(state: PagingState<Int, Item>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Item> {
        Log.d(TAG, "load: " + latLong)
        return try{
            val currentPage : Int = params.key ?: FIRST_PAGE_INDEX
            val response = fourSquareApiService.getRestaurantList("${latLong.lat},${latLong.long}",20,currentPage * 20)
            var nextPage : Int? = null
            if(response.response.totalResults > currentPage*20){
                nextPage = currentPage + 1
            }

            val restaurantList = response.response.groups.stream()
                .flatMap { it.items.stream() }
                .collect(Collectors.toList())



            LoadResult.Page(
                data = restaurantList,
                prevKey = null,
                nextKey = nextPage
            )
        }catch (e : Exception){
            LoadResult.Error(e)
        }
    }


    companion object {
        private const val FIRST_PAGE_INDEX = 0
    }
}