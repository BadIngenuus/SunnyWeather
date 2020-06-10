package com.android.sunnyweather.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.android.sunnyweather.model.WeatherRepository
import com.android.sunnyweather.model.data.Place

class PlaceViewModel : ViewModel() {

    private var queryPlace = MutableLiveData<String>()

    val placeList = Transformations.switchMap(queryPlace){
        Log.d(TAG,it)
        WeatherRepository.searchPlaces(it)
    }

    fun searchPlace(query : String){
        queryPlace.value = query
    }

    companion object{
        const val TAG = "PlaceViewModel"
    }

}