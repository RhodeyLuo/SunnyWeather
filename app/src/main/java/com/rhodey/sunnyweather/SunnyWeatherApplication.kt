package com.rhodey.sunnyweather

import android.app.Application
import android.content.Context

class SunnyWeatherApplication : Application() {
    companion object {
        lateinit var context: Context

        const val TOKEN = "WQ3vdy4kwRiXzSYJ"
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}