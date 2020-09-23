package com.rhodey.sunnyweather.ui.place


import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.rhodey.sunnyweather.MainActivity
import com.rhodey.sunnyweather.R
import com.rhodey.sunnyweather.logic.model.Place
import com.rhodey.sunnyweather.ui.weather.WeatherActivity
import kotlinx.android.synthetic.main.activity_weather.*


class PlaceAdapter(private val fragment: PlaceFragment, private val list: List<Place>) :
    RecyclerView.Adapter<PlaceAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val placeName: TextView = itemView.findViewById(R.id.placeName)
        val placeAddress: TextView = itemView.findViewById(R.id.placeAddress)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.place_item, parent, false)
        val viewHolder = ViewHolder(view)
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            val place = list[position]
            if (fragment.activity is MainActivity) {
                val intent = Intent(parent.context, WeatherActivity::class.java).apply {
                    putExtra("location_lng", place.location.lng)
                    putExtra("location_lat", place.location.lat)
                    putExtra("place_name", place.name)
                }
                fragment.startActivity(intent)
                fragment.activity?.finish()
            } else {
                val weatherActivity = fragment.activity as WeatherActivity
                weatherActivity.drawerLayout.closeDrawers()
                weatherActivity.viewModel.locationLng = place.location.lng
                weatherActivity.viewModel.locationLat = place.location.lat
                weatherActivity.viewModel.placeName = place.name
                weatherActivity.refreshWeather()
            }
            fragment.viewMode.savePlace(place)

        }
        return viewHolder
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.placeName.text = list[position].name
        holder.placeAddress.text = list[position].formatted_address
    }

}