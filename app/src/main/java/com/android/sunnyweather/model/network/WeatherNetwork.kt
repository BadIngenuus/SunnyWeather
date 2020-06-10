package com.android.sunnyweather.model.network

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.RuntimeException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine
import kotlin.math.ln

object WeatherNetwork {

    private val weatherService = ServiceCreator.create<WeatherService>()

    suspend fun searchPlaces(query : String) = weatherService.searchPlace(query).await()

    suspend fun getDailyWeather(lat : String,lng : String) = weatherService.getDailyWeather(lat, lng).await()

    suspend fun getRealtimeWeather(lat : String, lng : String) = weatherService.getRealTimeWeather(lat,lng).await()

    private suspend fun<T> Call<T>.await() : T {
        return suspendCoroutine {continuation ->
            enqueue(object : Callback<T>{
                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }

                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if (body != null)
                        continuation.resume(body)
                    else
                        continuation.resumeWithException(RuntimeException("response body is null"))
                }

            })
        }
    }

}