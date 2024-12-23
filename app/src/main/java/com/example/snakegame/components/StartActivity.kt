package com.example.snakegame.components

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.snakegame.databinding.ActivityStartBinding
import kotlin.system.exitProcess

class StartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStartBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        binding.canvas1.mapIndex = 0
        binding.canvas2.mapIndex = 1
        binding.canvas3.mapIndex = 2

        var mapSelection: Int? = null
        binding.canvas1.setOnClickListener() {
            binding.canvas1.changeSelectingState(true)
            binding.canvas2.changeSelectingState(false)
            binding.canvas3.changeSelectingState(false)
            mapSelection = 0
        }
        binding.canvas2.setOnClickListener() {
            binding.canvas1.changeSelectingState(false)
            binding.canvas2.changeSelectingState(true)
            binding.canvas3.changeSelectingState(false)
            mapSelection = 1
        }
        binding.canvas3.setOnClickListener() {
            binding.canvas1.changeSelectingState(false)
            binding.canvas2.changeSelectingState(false)
            binding.canvas3.changeSelectingState(true)
            mapSelection = 2
        }
        // Set click listener for Button 1 to navigate to NextActivity
        binding.button1.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            intent.putExtra("mapSelection", mapSelection)
            startActivity(intent)
        }

        // Optional: Set click listener for Button 2
        binding.button2.setOnClickListener {
            // Quit the app
            finish() // Ends the current activity
            exitProcess(0) // Ensures the app process is terminated
        }
    }
}