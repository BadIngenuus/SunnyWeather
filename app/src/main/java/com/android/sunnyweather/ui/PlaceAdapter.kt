package com.android.sunnyweather.ui

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.sunnyweather.WeatherApp
import com.android.sunnyweather.databinding.ItemPlaceBinding
import com.android.sunnyweather.model.data.Place

class PlaceAdapter : ListAdapter<Place,PlaceAdapter.PlaceViewHolder>(PlaceItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder =
        PlaceViewHolder(ItemPlaceBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class PlaceViewHolder(private val binding : ItemPlaceBinding) : RecyclerView.ViewHolder(binding.root){

        init {
            binding.itemPlaceContainer.setOnClickListener { view ->
                binding.place?.let {
                    Log.d("adapter", ":${it.location} ")
                    navigationToWeather(view,it.name,it.location.lat,it.location.lng)
                }
            }
        }

        private fun navigationToWeather(view : View,placeName : String,lat : String,lng : String){
            val inputMethodManage
                    = WeatherApp.appContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManage.hideSoftInputFromWindow(view.windowToken,0)
            val direction = PlaceFragmentDirections
                .actionPlaceFragmentToWeatherFragment(placeName,lat, lng)
            view.findNavController().navigate(direction)
        }

        fun bind(item : Place){
            binding.apply {
                place = item
                executePendingBindings()
            }
        }
    }

}

class PlaceItemCallback : DiffUtil.ItemCallback<Place>(){
    override fun areItemsTheSame(oldItem: Place, newItem: Place): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Place, newItem: Place): Boolean {
        return oldItem.name == newItem.name
    }

}