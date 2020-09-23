package com.rhodey.sunnyweather.logic


import androidx.core.content.edit
import androidx.lifecycle.liveData
import com.google.gson.Gson
import com.rhodey.sunnyweather.logic.dao.PlaceDao
import com.rhodey.sunnyweather.logic.model.Place
import com.rhodey.sunnyweather.logic.model.Weather
import com.rhodey.sunnyweather.logic.network.SunnyWeatherNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.lang.Exception
import java.lang.RuntimeException
import kotlin.coroutines.CoroutineContext


object Repository {
    fun searchPlaces(query: String) = fire(Dispatchers.IO) {
        val placeResponse = SunnyWeatherNetwork.searchPlace(query)
        if (placeResponse.status == "ok") {
            Result.success(placeResponse.places)
        } else {
            Result.failure(RuntimeException("response status is ${placeResponse.status}"))
        }
    }

    fun refreshWeather(lng: String, lat: String) = fire(Dispatchers.IO) {
        coroutineScope {
            val deferredRealtime = async { SunnyWeatherNetwork.getRealtimeResponse(lng, lat) }
            val deferredDaily = async { SunnyWeatherNetwork.getDailyResponse(lng, lat) }
            val realtimeResponse = deferredRealtime.await()
            val dailyResponse = deferredDaily.await()
            if (realtimeResponse.status == "ok" && dailyResponse.status == "ok") {
                Result.success(Weather(realtimeResponse, dailyResponse))
            } else {
                Result.failure(RuntimeException("realtime response status is ${realtimeResponse.status},daily response status is ${dailyResponse.status}"))
            }
        }
    }

    fun savePlace(place: Place) = PlaceDao.savePlace(place)

    fun getSavedPlace(): Place = PlaceDao.getSavedPlace()

    fun isPlaceSaved(): Boolean = PlaceDao.isPlaceSaved()

    private fun <T> fire(context: CoroutineContext, block: suspend () -> Result<T>) =
        liveData(context) {
            val result = try {
                block()
            } catch (e: Exception) {
                Result.failure<T>(e)
            }
            emit(result)
        }
}