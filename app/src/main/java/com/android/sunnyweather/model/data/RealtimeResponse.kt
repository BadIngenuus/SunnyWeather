package com.android.sunnyweather.model.data

import com.google.gson.annotations.SerializedName

data class RealtimeResponse(val status : String,val result : Result) {

    data class Result(val realtime: Realtime)

    data class Realtime(
        val  temperature : Float,  //温度
        @SerializedName("apparent_temperature")val apparentTemperature : Float,//体感温度
        val  pressure : Double,//气压
        val  humidity : Float,//相对湿度
        @SerializedName("skycon")val skyCon  : String ,  //主要天气现象
        @SerializedName("air_quality") val airQuality: AirQuality,
        val wind : Wind
    )

    data class Wind(val direction : Float,val speed : Float)

    data class AirQuality(
        val pm25 : Float,
        val pm10 : Float,
        val o3 : Float,
        val no2 : Float,
        val so2 : Float,
        val co : Float,
        val aqi : AQI
        )

    data class AQI(val chn : Float)

}