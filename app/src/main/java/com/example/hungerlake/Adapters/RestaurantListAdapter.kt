package com.example.hungerlake.Adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hungerlake.R
import com.example.hungerlake.databinding.ListRestaurantItemBinding
import java.util.ArrayList
import android.util.Log
import coil.load
import com.example.hungerlake.Listeners.RestaurantItemClickListener
import com.example.hungerlake.models.Category
import com.example.hungerlake.models.Item

class RestaurantListAdapter(private val onRestaurantItemClickListener: RestaurantItemClickListener) :
    RecyclerView.Adapter<RestaurantListAdapter.RestaurantItemViewHolder>() {

    private val TAG = "RestaurantListAdapter"
    private val icon_desc = "bg_32"
    private var restaurantList : List<Item> = ArrayList()

    fun setUpdatedRestaurantList(updatedRestaurantList : List<Item>){
        restaurantList = updatedRestaurantList
        notifyDataSetChanged()
    }

    class RestaurantItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ListRestaurantItemBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantItemViewHolder {
        return RestaurantItemViewHolder(LayoutInflater.from(parent.context).
                inflate(R.layout.list_restaurant_item,parent,false))
    }

    override fun getItemCount(): Int {
        Log.d(TAG + " size",restaurantList.size.toString())
        return restaurantList.size
    }

    override fun onBindViewHolder(holder: RestaurantItemViewHolder, position: Int) {
        with(holder){
            val restaurantVenue = restaurantList[position].venue
            binding.tvRestaurantName.text = restaurantVenue.name
            binding.tvRestaurantAddress.text = restaurantVenue.location.formattedAddress[0]
            binding.tvRestaurantDistance.text = restaurantVenue.location.distance.toString()

//            binding.ivThumbsDown.setOnClickListener {
//                if(restaurantVenue.thumbsDown != null && restaurantVenue.thumbsDown!!){
//                    binding.ivThumbsDown.setColorFilter(Color.rgb(133,133,133))
//                    restaurantVenue.thumbsDown = false
//                }
//                else{
//                    binding.ivThumbsDown.setColorFilter(Color.rgb(255,0,0))
//                    restaurantVenue.thumbsUp = false
//                    restaurantVenue.thumbsDown = true
//                }
//            }
            Log.d(TAG + " verified", restaurantVenue.verified.toString())
            if(restaurantVenue.verified){
                Log.d(TAG + " verified", binding.ivVerifiedTick.visibility.toString())
                binding.ivVerifiedTick.visibility = View.VISIBLE
            }
            else{
                binding.ivVerifiedTick.visibility = View.GONE
            }
            val primaryCategory : Category? = restaurantVenue.categories.find { it.primary }
            if(primaryCategory != null){
                val icon = primaryCategory.icon.prefix
                val suffix = primaryCategory.icon.suffix
                val url = "$icon$icon_desc$suffix"
                Log.d(TAG,url)
                binding.ivRestaurantIcon.load(url)
                binding.tvRestaurantType.text = primaryCategory.shortName
            }
        }
        holder.itemView.setOnClickListener{
            onRestaurantItemClickListener.onClick(it,restaurantList[position].venue)
        }
    }
}