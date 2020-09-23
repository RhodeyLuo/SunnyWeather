package com.rhodey.sunnyweather.logic.dao

import android.content.Context
import androidx.core.content.edit
import com.google.gson.Gson
import com.rhodey.sunnyweather.SunnyWeatherApplication
import com.rhodey.sunnyweather.logic.model.Place

object PlaceDao {
    fun savePlace(place: Place) {
        sharedPreferences().edit {
            putString("place", Gson().toJson(place))
        }
    }

    fun getSavedPlace(): Place {
        val str = sharedPreferences().getString("place", "")
        return Gson().fromJson(str, Place::class.java)
    }

    fun isPlaceSaved(): Boolean {
        return sharedPreferences().contains("place")
    }
}

private fun sharedPreferences() =
    SunnyWeatherApplication.context.getSharedPreferences("sunny_weather", Context.MODE_PRIVATE)
