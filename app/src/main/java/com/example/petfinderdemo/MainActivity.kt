package com.example.petfinderdemo

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private var btnFindPet : Button ?= null
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnFindPet = findViewById(R.id.button)
        btnFindPet?.setOnClickListener{
            Log.e("PetListActivity","Not opening, do something")
            val intent = Intent(this, LocationActivity::class.java)
            startActivity(intent)
        }

    }
}