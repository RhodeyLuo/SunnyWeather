package com.rhodey.sunnyweather.ui.weather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.rhodey.sunnyweather.formatLocation
import com.rhodey.sunnyweather.logic.Repository
import com.rhodey.sunnyweather.logic.model.Location

class WeatherViewModel : ViewModel() {
    private val locationLiveData = MutableLiveData<Location>()

    var locationLng = ""
    var locationLat = ""
    var placeName = ""

    val weatherLiveData = Transformations.switchMap(locationLiveData) {
        Repository.refreshWeather(it.lng.formatLocation(), it.lat.formatLocation())
    }

    fun refreshWeather(lng: String, lat: String) {
        locationLiveData.value = Location(lng = lng, lat = lat)
    }
}