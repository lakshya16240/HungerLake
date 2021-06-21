package com.example.hungerlake.Views.RestaurantItem

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.hungerlake.Adapters.RestaurantReviewsAdapter
import com.example.hungerlake.R
import com.example.hungerlake.ViewModels.RestaurantViewModel
import com.example.hungerlake.ViewModels.SelectedRestaurantViewModel
import com.example.hungerlake.databinding.RestaurantFragmentBinding
import com.example.hungerlake.models.Venue
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.schedule


class RestaurantFragment : Fragment() {

    private val selectedRestaurantViewModel by sharedViewModel<SelectedRestaurantViewModel>()
    private val restaurantViewModel: RestaurantViewModel by viewModel()
    private lateinit var restaurantFragmentBinding: RestaurantFragmentBinding
    private lateinit var selectedRestaurant : Venue
    private lateinit var restaurantReviewsAdapter: RestaurantReviewsAdapter
    private val TAG = "RestaurantFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Log.d("RestFragment", "onCreateView: ")
        val view = inflater.inflate(R.layout.restaurant_fragment, container, false)
        restaurantFragmentBinding = RestaurantFragmentBinding.bind(view)

        initRecyclerView(view)

        restaurantViewModel.getRestaurantDetails().observe(viewLifecycleOwner, Observer {
            Log.d(TAG, "onCreateView: " + it)
            restaurantViewModel.setReviewsList(it.reviews)
            updateView(it)
        })
        selectedRestaurantViewModel.getSelectedRestaurant().observe(viewLifecycleOwner, Observer {
            if (it != null) {
                selectedRestaurant = it
                restaurantViewModel.fetchRestaurantDetails(it.id)
                updateView(it)
            }
        })

        restaurantFragmentBinding.bvSubmitReview.setOnClickListener{
            val editTextRestaurantReview = restaurantFragmentBinding.etRestaurantReview
            val reviewText : String = editTextRestaurantReview.text.toString()

            restaurantViewModel.addReview(reviewText, selectedRestaurant.id)
            editTextRestaurantReview.text.clear()
            restaurantFragmentBinding.tvRestaurantName.requestFocus()

        }

        restaurantViewModel.getReviewsList().observe(viewLifecycleOwner, {
            Log.d(TAG, "onCreateView: reviewsSetCallback $it")
            restaurantReviewsAdapter.setUpdatedReviewsList(it)
        })


        return view
    }

    private fun initRecyclerView(view: View) {

        val restaurantReviewsRecyclerView = restaurantFragmentBinding.rvRestaurantReviews
        val linearLayoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        restaurantReviewsRecyclerView.layoutManager = linearLayoutManager
        restaurantReviewsAdapter = RestaurantReviewsAdapter()
        restaurantReviewsRecyclerView.adapter = restaurantReviewsAdapter


        val linearSnapHelper = LinearSnapHelper()
        linearSnapHelper.attachToRecyclerView(restaurantReviewsRecyclerView)

        Timer("AutoScroll", false).schedule(3000){
            val lastVisibleItemPosition = linearLayoutManager.findLastCompletelyVisibleItemPosition()
            if(lastVisibleItemPosition < restaurantReviewsAdapter.itemCount - 1){
                restaurantReviewsRecyclerView.smoothScrollToPosition(lastVisibleItemPosition + 1)
            }
            else{
                restaurantReviewsRecyclerView.smoothScrollToPosition(0)
//                linearLayoutManager.smoothScrollToPosition(restaurantReviewsRecyclerView,RecyclerView.State(),0)
            }
        }
    }

    private fun updateView(venue: Venue) {
        restaurantFragmentBinding.tvRestaurantName.text = venue.name
        restaurantFragmentBinding.tvRestaurantAddress.text = venue.address

        val primaryCategory : String? = venue.primaryCategory
        if(primaryCategory != null){
            restaurantFragmentBinding.tvRestaurantType.text = primaryCategory
        }else{
            restaurantFragmentBinding.tvRestaurantType.visibility = View.GONE
        }

        restaurantFragmentBinding.tvRestaurantType.text = venue.type

        if(venue.isOpen != null){
            lateinit var isRestaurantOpen : String
            if(venue.isOpen!!) {
                isRestaurantOpen = "Open"
                restaurantFragmentBinding.tvRestaurantIsOpen.setTextColor(Color.GREEN)
            } else {
                isRestaurantOpen = "Closed"
                restaurantFragmentBinding.tvRestaurantIsOpen.setTextColor(Color.RED)
            }
            restaurantFragmentBinding.tvRestaurantIsOpen.text = isRestaurantOpen
        }
        if(venue.verified){
            restaurantFragmentBinding.ivVerifiedTick.visibility = View.VISIBLE
        }

        if(venue.pricing != null){
            restaurantFragmentBinding.tvRestaurantPriceTier.text = venue.pricing
        }

        if(venue.photoUrl != null) {
            val photoUrl = venue.photoUrl
            val imageList = ArrayList<SlideModel>() // Create image list

            imageList.add(SlideModel(photoUrl, ScaleTypes.FIT))

            val imageSlider = restaurantFragmentBinding.restaurantImages
            imageSlider.setImageList(imageList)
        }

        if(venue.rating != null){
            restaurantFragmentBinding.restaurantRating.rating = ((venue.rating!!)/2).toFloat()
        }

        restaurantFragmentBinding.ivThumbsDown.setOnClickListener {
            Toast.makeText(
                context,
                "Thank you for your feedback! We won't show you this restaurant again.",
                Toast.LENGTH_LONG
            ).show()
            restaurantViewModel.updateRestaurantRejectPref(venue.id)
        }

        if(venue.bestTip != null) {
            restaurantFragmentBinding.tvRestaurantTip.visibility = View.VISIBLE
            restaurantFragmentBinding.tvRestaurantTip.text = venue.bestTip
        }
        else{
            restaurantFragmentBinding.tvRestaurantTip.visibility = View.GONE
        }

        restaurantFragmentBinding.menuButton.setOnClickListener{
            val uri: Uri =
                Uri.parse(venue.menuUrl)

            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }


        restaurantFragmentBinding.moreInfoButton.setOnClickListener{
            val uri: Uri =
                Uri.parse(venue.url)

            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
    }

}