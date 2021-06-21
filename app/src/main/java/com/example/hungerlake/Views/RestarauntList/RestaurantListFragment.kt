package com.example.hungerlake.Views.RestarauntList

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.filter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hungerlake.Adapters.RestaurantListPagingAdapter
import com.example.hungerlake.Listeners.RestaurantItemClickListener
import com.example.hungerlake.R
import com.example.hungerlake.ViewModels.RestaurantListViewModel
import com.example.hungerlake.ViewModels.RestaurantViewModel
import com.example.hungerlake.ViewModels.SelectedRestaurantViewModel
import com.example.hungerlake.Views.RestaurantItem.RestaurantFragment
import com.example.hungerlake.databinding.RestaurantListFragmentBinding
import com.example.hungerlake.models.Item
import com.example.hungerlake.models.VenueServiceEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.fragment.android.replace
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class RestaurantListFragment : Fragment(), RestaurantItemClickListener {

    private val restListViewModel: RestaurantListViewModel by viewModel()
    private val restaurantViewModel: RestaurantViewModel by viewModel()
    private val selectedRestaurantViewModel by sharedViewModel<SelectedRestaurantViewModel>()
    private lateinit var restaurantListPagingAdapter: RestaurantListPagingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        Log.d("Listingg","here")
        val view =  inflater.inflate(R.layout.restaurant_list_fragment, container, false)
        initRecyclerView(view)
        return view
    }

    private fun initRecyclerView(view: View) {

        val restaurantRecyclerView = RestaurantListFragmentBinding.bind(view).rvRestaurantList
        restaurantRecyclerView.layoutManager = LinearLayoutManager(activity)
        restaurantListPagingAdapter = RestaurantListPagingAdapter(this,restaurantViewModel)
        restaurantRecyclerView.adapter = restaurantListPagingAdapter

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        Log.d("RestListFragment", "onActivityCreated: " + selectedRestaurantViewModel)

        lifecycleScope.launchWhenCreated {
            restListViewModel.getRestaurantListing().collectLatest {
                restaurantListPagingAdapter.submitData(it)
            }
        }

    }

    override fun onClick(view: View, venue: VenueServiceEntity) {
        selectedRestaurantViewModel.setSelectedRestaurant(venue)
        val transaction = parentFragmentManager.beginTransaction()
        transaction.replace<RestaurantFragment>(android.R.id.content)
        transaction.addToBackStack(null)
        transaction.commit()
    }

}