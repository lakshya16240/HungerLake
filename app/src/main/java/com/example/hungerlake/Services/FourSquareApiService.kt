package com.example.hungerlake.Services

import com.example.hungerlake.models.Constants
import com.example.hungerlake.models.RestaurantDetailsApiResponse
import com.example.hungerlake.models.RestaurantListResponse
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FourSquareApiService {

    @GET("explore")
    suspend fun getRestaurantList(
        @Query("ll") latlong : String,
        @Query("limit") limit : Int,
        @Query("offset") offset : Int = 0,
        @Query("query") query : String = "restaurant"
    ) : RestaurantListResponse


    @GET("{restaurantId}")
    suspend fun getRestaurantDetails(
        @Path("restaurantId") venueId : String
    ) : RestaurantDetailsApiResponse

    companion object {

        operator fun invoke() : FourSquareApiService {
            val requestInterceptor = Interceptor {chain ->

                val url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("client_id", Constants.CLIENT_ID)
                    .addQueryParameter("client_secret",Constants.CLIENT_SECRET)
                    .addQueryParameter("v",Constants.API_VERSION)
                    .build()

                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()

                return@Interceptor chain.proceed(request)
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://api.foursquare.com/v2/venues/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(FourSquareApiService::class.java)
        }
    }
}