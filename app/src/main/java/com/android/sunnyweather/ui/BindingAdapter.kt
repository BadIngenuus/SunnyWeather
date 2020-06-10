package com.android.sunnyweather.ui

import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.android.sunnyweather.WeatherApp
import com.android.sunnyweather.model.data.DailyResponse
import com.android.sunnyweather.model.data.RealtimeResponse
import com.bumptech.glide.Glide

@BindingAdapter("selectWeatherBg")
fun selectWeatherBg(image: ImageView,skyCon : String?) {
    if (skyCon == null)
        return
    image.setBackgroundResource(getSky(skyCon).bg)
}

@BindingAdapter("selectWeatherIcon")
fun selectWeatherIcon(image : ImageView,skyCon: String?){
    if (skyCon == null)
        return
    Glide.with(WeatherApp.appContext).load(getSky(skyCon).icon).into(image)
}

@BindingAdapter("selectSkyConAndSetAQI")
fun selectSkyConAndSetAQI(textView: TextView,realtime : RealtimeResponse.Realtime?){
    if (realtime == null)
        return
    val string = "${getSky(realtime.skyCon).info} | 空气质量 ${realtime.airQuality.aqi.chn}"
    textView.text = string
}

@BindingAdapter("temperature")
fun setTemperature(textView: TextView,temperature: Float?){
    if (temperature == null)
        return
    val str = "${temperature.toInt()} ℃"
    textView.text = str
}
