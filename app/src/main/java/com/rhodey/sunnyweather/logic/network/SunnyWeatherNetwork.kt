package com.rhodey.sunnyweather.logic.network

import com.rhodey.sunnyweather.logic.model.PlaceResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.await
import retrofit2.http.Query
import java.lang.Exception
import java.lang.RuntimeException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object SunnyWeatherNetwork {
    private val placeService = ServiceCreator.create<PlaceService>()
    private val weatherService = ServiceCreator.create<WeatherService>()

    suspend fun searchPlace(query: String) = placeService.searchPlace(query).await()

    suspend fun getRealtimeResponse(lng: String, lat: String) =
        weatherService.getRealtimeWeather(lng, lat).await()

    suspend fun getDailyResponse(lng: String, lat: String) =
        weatherService.getDailyWeather(lng, lat).await()

    private suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine {
            enqueue(object : Callback<T> {
                override fun onFailure(call: Call<T>, t: Throwable) {
                    it.resumeWithException(t)
                }

                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if (body != null) {
                        it.resume(body)
                    } else {
                        it.resumeWithException(RuntimeException("body is null"))
                    }
                }

            })
        }
    }
}