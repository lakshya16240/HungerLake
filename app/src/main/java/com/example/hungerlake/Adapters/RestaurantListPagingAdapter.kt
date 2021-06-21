package com.example.hungerlake.Adapters

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.hungerlake.Listeners.RestaurantItemClickListener
import com.example.hungerlake.R
import com.example.hungerlake.ViewModels.RestaurantListViewModel
import com.example.hungerlake.ViewModels.RestaurantViewModel
import com.example.hungerlake.databinding.ListRestaurantItemBinding
import com.example.hungerlake.models.Category
import com.example.hungerlake.models.Item
import com.example.hungerlake.utils.DiffUtilCallback

class RestaurantListPagingAdapter(
    private val onRestaurantItemClickListener: RestaurantItemClickListener,
    private val restaurantViewModel: RestaurantViewModel
) :
    PagingDataAdapter<Item,RestaurantListPagingAdapter.RestaurantItemViewHolder>(DiffUtilCallback()) {


    private val TAG = "RestaurantListPagingAdapter"
    private val icon_desc = "bg_32"

    class RestaurantItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ListRestaurantItemBinding.bind(itemView)
    }

    override fun onBindViewHolder(holder: RestaurantItemViewHolder, position: Int) {
        val restaurantVenue = getItem(position)!!.venue

//        if(!restaurantViewModel.getRestaurantRejectPref(restaurantVenue.id)) {
            with(holder) {
                binding.tvRestaurantName.text = restaurantVenue.name
                binding.tvRestaurantAddress.text = restaurantVenue.location.formattedAddress[0]
                binding.tvRestaurantDistance.text = restaurantVenue.location.distance.toString()

//                binding.ivThumbsDown.setOnClickListener {
//                    restaurantViewModel.updateRestaurantRejectPref(restaurantVenue.id)
//                }

                if (restaurantVenue.verified) {
                    binding.ivVerifiedTick.visibility = View.VISIBLE
                } else {
                    binding.ivVerifiedTick.visibility = View.GONE
                }
                val primaryCategory: Category? = restaurantVenue.categories.find { it.primary }
                if (primaryCategory != null) {
                    val icon = primaryCategory.icon.prefix
                    val suffix = primaryCategory.icon.suffix
                    val url = "$icon$icon_desc$suffix"
                    binding.ivRestaurantIcon.load(url)
                    binding.tvRestaurantType.text = primaryCategory.shortName
                }
            }
            holder.itemView.setOnClickListener {
                onRestaurantItemClickListener.onClick(it, restaurantVenue)
            }
//        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantItemViewHolder {
        return RestaurantItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.list_restaurant_item, parent, false)
        )
    }
}