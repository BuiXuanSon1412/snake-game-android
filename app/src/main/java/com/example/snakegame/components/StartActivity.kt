package com.example.snakegame.components

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.snakegame.R
import com.example.snakegame.animations.MapView
import com.example.snakegame.databinding.ActivityStartBinding
import com.example.snakegame.utils.ScoreManager
import kotlin.system.exitProcess

class StartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStartBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        binding.mapView0.mapIndex = 0
        binding.mapView1.mapIndex = 1
        binding.mapView2.mapIndex = 2
        binding.mapView0.changeSelectingState(true)

        val highScore = ScoreManager.getHighScore(this)
        binding.highScoreTextView.text = "Score: $highScore"
        var mapIndex: Int? = null
        binding.mapView0.setOnClickListener() {
            binding.mapView0.changeSelectingState(true)
            binding.mapView1.changeSelectingState(false)
            binding.mapView2.changeSelectingState(false)
            mapIndex = 0
        }
        binding.mapView1.setOnClickListener() {
            binding.mapView0.changeSelectingState(false)
            binding.mapView1.changeSelectingState(true)
            binding.mapView2.changeSelectingState(false)
            mapIndex = 1
        }
        binding.mapView2.setOnClickListener() {
            binding.mapView0.changeSelectingState(false)
            binding.mapView1.changeSelectingState(false)
            binding.mapView2.changeSelectingState(true)
            mapIndex = 2
        }

        binding.buttonPlay.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            intent.putExtra("mapIndex", mapIndex)
            startActivity(intent)
            finish()
        }

        // Optional: Set click listener for Button 2
        binding.buttonExit.setOnClickListener {
            // Quit the app
            finishAffinity() // Closes all activities
            exitProcess(0) // Ensures the app process is terminated
        }
    }
}