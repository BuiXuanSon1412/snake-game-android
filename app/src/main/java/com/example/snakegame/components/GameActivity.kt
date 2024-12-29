package com.example.snakegame.components

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import com.example.snakegame.R
import com.example.snakegame.databinding.ActivityGameBinding
import com.example.snakegame.animations.GameView
import com.example.snakegame.engine.Game
import kotlinx.android.synthetic.main.activity_game.*
import kotlinx.coroutines.*
import kotlin.math.abs

class GameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGameBinding
    private lateinit var game: Game
    private var lastTime: Long = 0
    private val frameRate = 60
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        supportActionBar?.hide()

        // retrieve data from intent
        val mapIndex = intent.getIntExtra("mapSelection", 0)
        game = Game(mapIndex)
        val gameView = GameView(this, null, game = game)
        findViewById<FrameLayout>(R.id.game_view_container).addView(gameView)
        gameView.invalidate()
        // transfer data to animation display
        //val canvas = binding.canvas
        //canvas.mapIndex = mapIndex
        //canvas.game = Game(mapIndex)
        //canvas.gameReady = true
        //canvas.invalidate()

        //val game = canvas.game
        //game.score.observe(this, Observer { score -> scoreTextView.text = "Score: $score" })
        // move the snake
        CoroutineScope(Dispatchers.IO).launch {

            while (true) {
                if (game.checkCollided()) {
                    game.state = 3
                    //openSettingsFragment()
                }
                if (game.state == 1) {
                    gameView.updateAnimation()

                    //game speed in millisecond
                    gameView.invalidate()
                    val currentTime = System.currentTimeMillis()
                    val timeToWait = 1000 / frameRate - (currentTime - lastTime)
                    if (timeToWait > 0) {
                        delay(timeToWait)
                    }
                    lastTime = currentTime
                }
            }
        }
        open class OnSwipeTouchListener : View.OnTouchListener {

            private val gestureDetector = GestureDetector(GestureListener())

            fun onTouch(event: MotionEvent): Boolean {
                return gestureDetector.onTouchEvent(event)
            }

            private inner class GestureListener : GestureDetector.SimpleOnGestureListener() {

                private val swipeThreshold = 100
                private val swipeVelocityThreshold = 100

                override fun onDown(e: MotionEvent): Boolean {
                    return true
                }

                override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
                    onTouch(e)
                    return true
                }

                override fun onFling(
                    e1: MotionEvent?,
                    e2: MotionEvent,
                    velocityX: Float,
                    velocityY: Float
                ): Boolean {
                    val result = false
                    try {
                        val diffY = e2.y - (e1?.y ?: 0f)
                        val diffX = e2.x - (e1?.x ?: 0f)
                        if (abs(diffX) > abs(diffY)) {
                            if (abs(diffX) > swipeThreshold && abs(velocityX) > swipeVelocityThreshold) {
                                if (diffX > 0) {
                                    onSwipeRight()
                                } else {
                                    onSwipeLeft()
                                }
                            }
                        } else {
                            // this is either a bottom or top swipe.
                            if (abs(diffY) > swipeThreshold && abs(velocityY) > swipeVelocityThreshold) {
                                if (diffY > 0) {
                                    onSwipeTop()
                                } else {
                                    onSwipeBottom()
                                }
                            }
                        }
                    } catch (exception: Exception) {
                        exception.printStackTrace()
                    }
                    return result
                }
            }

            @SuppressLint("ClickableViewAccessibility")
            override fun onTouch(v: View, event: MotionEvent): Boolean {
                return gestureDetector.onTouchEvent(event)
            }

            open fun onSwipeRight() {}
            open fun onSwipeLeft() {}
            open fun onSwipeTop() {}
            open fun onSwipeBottom() {}
        }
        gameView.setOnTouchListener(object : OnSwipeTouchListener() {

            override fun onSwipeLeft() {
                game.state = 1
                if (game.direction != "right")
                    game.direction = "left"
            }

            override fun onSwipeRight() {
                game.state = 1
                if (game.direction != "left")
                    game.direction = "right"
            }

            override fun onSwipeTop() {
                game.state = 1
                if (game.direction != "up")
                    game.direction = "down"
            }
            override fun onSwipeBottom() {
                game.state = 1
                if (game.direction != "down")
                    game.direction = "up"
            }
        })
        // button to control flow
        binding.buttonSettings.setOnClickListener {
            game.state = 2
            openSettingsFragment()
        }
        binding.buttonUp.setOnClickListener {
            game.state = 1
            if (game.direction != "down")
                game.direction = "up"
        }
        binding.buttonDown.setOnClickListener {
            game.state = 1
            if (game.direction != "up")
                game.direction = "down"
        }
        binding.buttonLeft.setOnClickListener {
            game.state = 1
            if (game.direction != "right")
                game.direction = "left"
        }
        binding.buttonRight.setOnClickListener {
            game.state = 1
            if (game.direction != "left")
                game.direction = "right"
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