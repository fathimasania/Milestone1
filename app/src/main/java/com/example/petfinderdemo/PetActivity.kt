package com.example.petfinderdemo

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class PetActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PetAdapter
    var customProgressDialog : Dialog ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pet)
        val toolbar = findViewById<Toolbar>(R.id.toolbar2)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back_arrow)
        val location = intent.getStringExtra("location")
        toolbar.title = "Pets In $location"
        // Handle click on back button
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        val breedList = intent.getStringArrayListExtra("breeds")
        recyclerView = findViewById(R.id.petRv)
        recyclerView.layoutManager = GridLayoutManager(this,2)
        adapter = PetAdapter(emptyList(), emptyList())
        recyclerView.adapter = adapter
        breedList?.let {
            showProgressDialog()
            fetchImageandbreeds(it)
        }

    }

    @SuppressLint("ResourceType")
    private fun fetchImageandbreeds(breeds : List<String>){
        lifecycleScope.launch {

            val imageUrls = mutableListOf<String>()
                for (breed in breeds) {
                    try {
                        val response = imageService.getImages(breed)
                        if (response.status == "success") {
                            imageUrls.add(response.message[0])
                        } else {
                            imageUrls.add("")
                        }
                }catch (e: Exception) {
                    Log.e(
                        "PetActivity",
                        "Error fetching images for breed $breed: ${e.message}", e)
                        imageUrls.add("")
                }
            }
            Log.d("PetActivity", "Fetched ${imageUrls.size} images")
            fun capitalizeBreeds(breeds: List<String>) : List<String>{
                return breeds.map { it.capitalize() }
            }
            val capitalizedBreeds = capitalizeBreeds(breeds)
            adapter.updateData(capitalizedBreeds,imageUrls)
            cancelProgressDialog()
        }
    }
    private fun showProgressDialog(){
        customProgressDialog = Dialog(this)
        customProgressDialog?.setContentView(R.layout.custom_progress_dialog)
        customProgressDialog!!.show()
    }

    private fun cancelProgressDialog() {
        if(customProgressDialog != null) {
            customProgressDialog?.dismiss()
            customProgressDialog=null
        }
    }
}