package com.example.hungerlake.utils

import coil.ImageLoader
import com.example.hungerlake.Data.RestaurantDatabase
import com.example.hungerlake.Mappers.RestaurantDTOMapper
import com.example.hungerlake.Services.RestaurantItemPagingSourceService
import com.example.hungerlake.Repository.RestaurantRepositoryImpl
import com.example.hungerlake.ViewModels.RestaurantListViewModel
import com.example.hungerlake.ViewModels.RestaurantViewModel
import com.example.hungerlake.ViewModels.SelectedRestaurantViewModel
import com.example.hungerlake.Views.RestarauntList.RestaurantListFragment
import com.example.hungerlake.Views.RestaurantItem.RestaurantFragment
import com.example.hungerlake.Services.FourSquareApiService
import com.example.hungerlake.models.LatLong
import org.koin.androidx.fragment.dsl.fragment
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val restAppModule = module {

    single { FourSquareApiService() }
    single { RestaurantItemPagingSourceService(get(), LatLong()) }
    single { RestaurantDTOMapper() }
    single { RestaurantDatabase.getInstance(get()) }
    single { RestaurantRepositoryImpl(get(),get(),get<RestaurantDatabase>().roomNoteDao()) }
    viewModel { RestaurantListViewModel(get()) }
    viewModel { RestaurantViewModel(get()) }
    viewModel { SelectedRestaurantViewModel(get()) }
    fragment { RestaurantListFragment() }
    fragment { RestaurantFragment() }
    single { ImageLoader.Builder(get())
            .availableMemoryPercentage(0.25)
            .crossfade(true)
            .build() }
}