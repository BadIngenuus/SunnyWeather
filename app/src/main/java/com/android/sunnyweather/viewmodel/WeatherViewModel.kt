package com.android.sunnyweather.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.sunnyweather.model.WeatherRepository
import com.android.sunnyweather.model.data.Weather
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class WeatherViewModel(var placeName : ObservableField<String>,var lat : String,var lng : String) : ViewModel() {

    private val _weather = MutableLiveData<Weather>()
    val weather : LiveData<Weather>
        get() = _weather

    private val _weatherFailure = MutableLiveData<Throwable>()
    val weatherFailure : LiveData<Throwable>
        get() = _weatherFailure

    fun refreshWeather(){
        viewModelScope.launch (Dispatchers.IO){
            coroutineScope {
                try {
                    val deferredRealtime = async {
                        WeatherRepository.getRealtimeWeather(lat, lng)
                    }
                    val deferredDaily = async {
                        WeatherRepository.getDailyWeather(lat, lng)
                    }
                    val realtimeResponse = deferredRealtime.await()
                    val dailyResponse = deferredDaily.await()
                    if (realtimeResponse.status == "ok" && dailyResponse.status == "ok"){
                        val weather = Weather(
                                realtimeResponse.result.realtime,
                                dailyResponse.result.daily
                            )
                        _weather.postValue(weather)
                    }else{
                        _weatherFailure.postValue(RuntimeException("realtime status is ${realtimeResponse.status} " +
                                "daily status is ${dailyResponse.status}"))
                    }
                }catch (e : Exception){
                    _weatherFailure.postValue(e)
                }
            }
        }
    }
}