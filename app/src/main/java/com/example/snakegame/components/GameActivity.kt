package com.example.snakegame.components

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import com.example.snakegame.R
import com.example.snakegame.databinding.ActivityGameBinding
import com.example.snakegame.objects.Food
import com.example.snakegame.objects.Game
import com.example.snakegame.objects.Snake
import kotlinx.android.synthetic.main.activity_game.*
import kotlinx.coroutines.*
import kotlin.math.abs

class GameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        supportActionBar?.hide()

        // retrieve data from intent
        val mapIndex = intent.getIntExtra("mapSelection", 0)

        // transfer data to animation display
        val canvas = binding.canvas
        canvas.mapIndex = mapIndex
        canvas.game = Game(mapIndex)
        canvas.gameReady = true
        canvas.invalidate()

        val game = canvas.game
        game.score.observe(this, Observer { score -> scoreTextView.text = "Score: $score" })
        // move the snake
        CoroutineScope(Dispatchers.IO).launch {

            while (true) {
                while (game.state) {
                    canvas.updateAnimation()

                    //game speed in millisecond
                    canvas.invalidate()
                    delay(500)
                }
            }
        }

        // button to control flow
        binding.buttonSettings.setOnClickListener {
            openSettingsFragment()
        }
        binding.buttonUp.setOnClickListener {
            canvas.game.state = true
            if (canvas.game.snake.direction != "down")
                canvas.game.snake.direction = "up"
        }
        binding.buttonDown.setOnClickListener {
            canvas.game.state = true
            if (canvas.game.snake.direction != "up")
                canvas.game.snake.direction = "down"
        }
        binding.buttonLeft.setOnClickListener {
            canvas.game.state = true
            if (canvas.game.snake.direction != "right")
                canvas.game.snake.direction = "left"
        }
        binding.buttonRight.setOnClickListener {
            canvas.game.state = true
            if (canvas.game.snake.direction != "left")
                canvas.game.snake.direction = "right"
        }

    }
    // Method to replace the current fragment with SettingsFragment
    private fun openSettingsFragment() {
        // Create a new instance of the SettingsFragment
        val settingsFragment = SettingsFragment()

        // Replace the current fragment with the new SettingsFragment
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, settingsFragment) // Replace with the container ID
            .addToBackStack(null) // Add this transaction to the back stack
            .commit()
    }

}