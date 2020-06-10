package com.android.sunnyweather.model

import androidx.lifecycle.liveData
import com.android.sunnyweather.model.data.Place
import com.android.sunnyweather.model.data.PlaceResponse
import com.android.sunnyweather.model.network.WeatherNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.lang.Exception
import java.lang.RuntimeException
import kotlin.coroutines.CoroutineContext
import kotlin.math.ln

object WeatherRepository {

    fun searchPlaces(query : String) = fire(Dispatchers.IO){
        val placeResponse = WeatherNetwork.searchPlaces(query)
        if (placeResponse.status == "ok"){
            val places = placeResponse.places
            Result.success(places)
        }else {
            Result.failure(RuntimeException("response status is ${placeResponse.status}"))
        }
    }

    suspend fun getDailyWeather(lat : String, lng : String) = WeatherNetwork.getDailyWeather(lat,lng)

    suspend fun getRealtimeWeather(lat: String,lng: String) = WeatherNetwork.getRealtimeWeather(lat, lng)

    private fun<T> fire(context : CoroutineContext,block : suspend () -> Result<T>) = liveData<Result<T>>(context){
        val result = try {
            block()
        }catch (e : Exception){
            Result.failure<T>(e)
        }
        emit(result)
    }

}