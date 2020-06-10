package com.android.sunnyweather

import android.app.Application
import android.content.Context

class WeatherApp : Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }

    companion object{
        lateinit var appContext : Context
        const val TOKEN = "F6FpeVaXQXs9T3A9"
    }

}