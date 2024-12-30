package com.example.snakegame.components

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import com.example.snakegame.R
import com.example.snakegame.databinding.ActivityGameBinding
import com.example.snakegame.animations.GameView
import com.example.snakegame.engine.Game
import com.example.snakegame.utils.ScoreManager
import kotlinx.android.synthetic.main.activity_game.scoreTextView
import kotlinx.coroutines.*
import kotlin.math.abs

class GameActivity : AppCompatActivity(),
    SettingFragment.GameResumeListener,
    GameOverFragment.GameRestartListener,
    GameView.GameOverListener {
    private lateinit var binding: ActivityGameBinding
    private lateinit var game: Game
    private lateinit var gameView: GameView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // retrieve data from intent
        val mapIndex = intent.getIntExtra("mapIndex", 0)
        game = Game(mapIndex)
        gameView = GameView(this, null, game = game)
        gameView.setGameOverListener(this)
        binding.gameViewContainer.addView(gameView)
        gameView.invalidate()

        game.score.observe(this) {
            newScore -> scoreTextView.text = "Score: $newScore"
        }
        // move the snake

        CoroutineScope(Dispatchers.Default).launch {
            while (true) {
                if (gameView.state == "running") {
                    //Log.d("Animation state", gameView.state)
                    //if (gameView.state == "waiting") gameView.invalidate()
                    if (gameView.updateAnimation()) {
                        gameView.invalidate()
                        //game speed in millisecond
                        delay(200)          // need more fixes to get better rendering
                    }
                    else {
                        val highScore = ScoreManager.getHighScore(this@GameActivity)
                        if (game.score.value!! > highScore) {
                            ScoreManager.saveHighScore(this@GameActivity, game.score.value!!)
                        }
                    }
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
                if (gameView.state == "waiting") gameView.state = "running"
                if (gameView.state == "running" && game.direction != "right")
                    game.redirection = "left"
            }

            override fun onSwipeRight() {
                if (gameView.state == "waiting") gameView.state = "running"
                if (gameView.state == "running" && game.direction != "left")
                    game.redirection = "right"
            }

            override fun onSwipeTop() {
                if (gameView.state == "waiting") gameView.state = "running"
                if (gameView.state == "running" && game.direction != "down")
                    game.redirection = "up"
            }
            override fun onSwipeBottom() {
                if (gameView.state == "waiting") gameView.state = "running"
                if (gameView.state == "running" && game.direction != "up")
                    game.redirection = "down"
            }
        })
        // button to control flow
        binding.buttonSettings.setOnClickListener {
            //game.state = 2
            if (gameView.state == "running") {
                gameView.state = "idle"
                openSettingsFragment()
            }
        }
        binding.buttonUp.setOnClickListener {
            if (gameView.state == "waiting") gameView.state = "running"
            if (gameView.state == "running" && game.direction != "down")
                game.redirection = "up"
        }
        binding.buttonDown.setOnClickListener {
            if (gameView.state == "waiting") gameView.state = "running"
            if (gameView.state == "running" && game.direction != "up")
                game.redirection = "down"
        }
        binding.buttonLeft.setOnClickListener {
            if (gameView.state == "waiting") gameView.state = "running"
            if (gameView.state == "running" && game.direction != "right")
                game.redirection = "left"
        }
        binding.buttonRight.setOnClickListener {
            if (gameView.state == "waiting") gameView.state = "running"
            if (gameView.state == "running" && game.direction != "left")
                game.redirection = "right"
        }

    }

    // Method to replace the current fragment with SettingsFragment
    private fun openSettingsFragment() {
        // Create a new instance of the SettingsFragment
        val settingsFragment = SettingFragment()
        // Replace the current fragment with the new SettingsFragment
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, settingsFragment) // Replace with the container ID
            .addToBackStack(null) // Add this transaction to the back stack
            .commit()
    }
    override fun onResumeButtonClicked() {
        gameView.state = "running"
    }

    override fun onRestartButtonClicked() {
        gameView.state = "waiting"
        game.reset()
        gameView.invalidate()
    }
    override fun onGameOver() {
        val gameOverFragment = GameOverFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, gameOverFragment) // Replace with the container ID
            //.addToBackStack(null) // Add this transaction to the back stack
            .commit()

    }

}
