package com.example.petfinderdemo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class LocationAdapter(
    private var locationList: List<String>,
    private var onItemClick : (String) -> Unit
) : RecyclerView.Adapter<LocationAdapter.LocationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.location_item, parent, false)
        return LocationViewHolder(view)
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        val location = locationList[position]
        holder.bindItem(location)
    }

    override fun getItemCount(): Int {
        return locationList.size
    }

    inner class LocationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val locationName: TextView = itemView.findViewById(R.id.locationName)
        fun bindItem(location: String) {
            locationName.text = location
            itemView.setOnClickListener {
                onItemClick(location)
            }
        }
    }
    fun filterList(filterList : List<String>){
        locationList = filterList
        notifyDataSetChanged()
    }

    fun updateData(newList: List<String>) {
        locationList = newList
        notifyDataSetChanged()
    }
}
