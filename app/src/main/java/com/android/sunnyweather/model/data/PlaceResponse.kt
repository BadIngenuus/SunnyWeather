package com.android.sunnyweather.model.data

import com.google.gson.annotations.SerializedName

data class PlaceResponse(val places : List<Place>, val query : String, val status : String)

data class Place(val name : String,
                 val location: Location,
                 @SerializedName("formatted_address") val address : String)

data class Location(val lat : String,val lng : String)