package com.example.petfinderdemo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.ArrayList
import java.util.Locale

class LocationActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: LocationAdapter
    private lateinit var originalLocationList : List<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)

        recyclerView = findViewById(R.id.locationRv)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = LocationAdapter(emptyList()) { location ->
            fetchBreedList(location)
        }

        recyclerView.adapter = adapter
        // Toolbar setup
        val toolbar = findViewById<Toolbar>(R.id.toolbar1)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back_arrow)

        // Handle click on back button
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        fetchLocation()
    }

    private fun fetchLocation() {
        Log.e("PetListActivity", "Trying to fetch locations...")
        GlobalScope.launch() {
            try {
                Log.e("PetListActivity", "Launched successfully")
                val response = locationService.getLocations()
                Log.e("PetListActivity", "Got response")
                withContext(Dispatchers.Main) {
                    val locationList = response.keys.toList()


                    fun captitalizeLocations(locations : List<String>) : List<String>{
                        return locations.map { it.capitalize() }
                    }
                    val capitalizedLocation = captitalizeLocations(locationList)
                    originalLocationList = capitalizedLocation
                    Log.e("PetListActivity", "Successfully fetched locations: $locationList")
                    adapter.updateData(capitalizedLocation)

                    Log.e("PetListActivity", "Successfully updated adapter")


                }
            } catch (e: Exception) {
                Log.e("LocationActivity", "Error fetching locations: ${e.message}")
                // Handle error or show a user-friendly message
            }
        }
    }

    private fun fetchBreedList(location : String){
        GlobalScope.launch {
            val response = withContext(Dispatchers.IO){
                locationService.getLocations()
            }

            val breedList = response[location.toLowerCase()] ?: emptyList()
            val intent = Intent(this@LocationActivity,PetActivity::class.java)
            intent.putStringArrayListExtra("breeds",ArrayList(breedList))
            intent.putExtra("location",location)
            startActivity(intent)
        }


    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.search_menu, menu)

        // Get the search view from the menu
        val searchItem = menu.findItem(R.id.actionSearch)
        val searchView = searchItem.actionView as SearchView

        // Set up search view with query listener
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Filter adapter data based on the query text
                val filteredList = originalLocationList.filter {
                    it.lowercase(Locale.getDefault()).contains(newText?.lowercase(Locale.getDefault()) ?: "")
                }
                if(filteredList.isEmpty()){
                    Toast.makeText(this@LocationActivity,"No Result Found",Toast.LENGTH_LONG).show()
                }
                adapter.filterList(filteredList)
                return true
            }
        })

        return true
    }

}

