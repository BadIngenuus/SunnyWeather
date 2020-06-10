package com.android.sunnyweather.model.network

import com.android.sunnyweather.WeatherApp
import com.android.sunnyweather.model.data.DailyResponse
import com.android.sunnyweather.model.data.Location
import com.android.sunnyweather.model.data.PlaceResponse
import com.android.sunnyweather.model.data.RealtimeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherService {

    @GET("v2/place?token=${WeatherApp.TOKEN}&lang=zh_CN")
    fun searchPlace(@Query("query") queryPlace: String) : Call<PlaceResponse>

    @GET("v2.5/${WeatherApp.TOKEN}/{lng},{lat}/realtime.json")
    fun getRealTimeWeather(@Path("lat")lat : String,@Path("lng")lng : String) : Call<RealtimeResponse>

    @GET("v2.5/${WeatherApp.TOKEN}/{lng},{lat}/daily.json")
    fun getDailyWeather(@Path("lat")lat : String,@Path("lng")lng : String)  : Call<DailyResponse>

}

//39.9041999  lat
//116.4073963  lng
// lng,lat