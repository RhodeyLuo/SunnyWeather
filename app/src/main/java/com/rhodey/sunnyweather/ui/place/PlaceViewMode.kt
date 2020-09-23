package com.rhodey.sunnyweather.ui.place

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.rhodey.sunnyweather.logic.Repository
import com.rhodey.sunnyweather.logic.dao.PlaceDao
import com.rhodey.sunnyweather.logic.model.Place

class PlaceViewMode : ViewModel() {
    private val searchLiveData = MutableLiveData<String>()

    val placeList = arrayListOf<Place>()

    val placeLiveData = Transformations.switchMap(searchLiveData) {
        Repository.searchPlaces(it)
    }

    fun searchPlaces(query: String) {
        searchLiveData.value = query
    }

    fun savePlace(place: Place) = Repository.savePlace(place)

    fun getSavedPlace(): Place = Repository.getSavedPlace()

    fun isPlaceSaved(): Boolean = Repository.isPlaceSaved()
}