package com.example.snakegame.component

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

        // Set click listener for Button 1 to navigate to NextActivity
        binding.button1.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
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