package com.example.hungerlake.Adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hungerlake.R
import com.example.hungerlake.databinding.ListReviewItemBinding
import com.example.hungerlake.models.ReviewEntity
import java.util.*

class RestaurantReviewsAdapter : RecyclerView.Adapter<RestaurantReviewsAdapter.ReviewItemViewHolder>()  {

    private var reviewList : List<ReviewEntity> = ArrayList<ReviewEntity>()
    private val TAG = "RestaurantReviewsAdapter"

    class ReviewItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ListReviewItemBinding.bind(itemView)
    }

    fun setUpdatedReviewsList(updatedReviewsList : List<ReviewEntity>){
        reviewList = updatedReviewsList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewItemViewHolder {
        Log.d(TAG, "onCreateViewHolder: ")
        return ReviewItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.list_review_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ReviewItemViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder: " + reviewList[position])
        with(holder){
            binding.tvReview.text = reviewList[position].reviewText
            binding.tvReviewUser.text = "- ${reviewList[position].username}"
        }
    }

    override fun getItemCount(): Int {
        Log.d(TAG, "getItemCount: " + reviewList.size)
        return reviewList.size
    }
}