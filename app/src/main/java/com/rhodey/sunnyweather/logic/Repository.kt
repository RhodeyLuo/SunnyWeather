package com.rhodey.sunnyweather.logic

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.rhodey.sunnyweather.logic.model.Place
import com.rhodey.sunnyweather.logic.model.PlaceResponse
import com.rhodey.sunnyweather.logic.network.SunnyWeatherNetwork
import com.rhodey.sunnyweather.logic.network.SunnyWeatherNetwork.searchPlace
import kotlinx.coroutines.Dispatchers
import java.lang.Exception
import java.lang.RuntimeException


object Repository {
    fun searchPlaces(query: String) = liveData(Dispatchers.IO) {
        val result = try {
            val placeResponse = SunnyWeatherNetwork.searchPlace(query)
            if (placeResponse.status == "ok") {
                Result.success(placeResponse.places)
            } else {
                Result.failure(RuntimeException("response status is ${placeResponse.status}"))
            }
        } catch (e: Exception) {
            Result.failure<List<Place>>(e)
        }
        emit(result)
    }
}