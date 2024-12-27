package com.example.snakegame.components

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import com.example.snakegame.databinding.ActivityGameBinding
import com.example.snakegame.objects.Food
import com.example.snakegame.objects.Game
import com.example.snakegame.objects.Snake
import kotlinx.android.synthetic.main.activity_game.*
import kotlinx.coroutines.*
import kotlin.math.abs

class GameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGameBinding
    private lateinit var game: Game
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        supportActionBar?.hide()

        val canvas = binding.canvas
        var mapIndex = intent.getIntExtra("mapSelection", 0)
        canvas.mapIndex = mapIndex
        game = Game(mapIndex, canvas.width, canvas.height)
        // touch control
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


        canvas.setOnTouchListener(object : OnSwipeTouchListener() {

            override fun onSwipeLeft() {
                Snake.alive = true
                if (Snake.direction != "right")
                    Snake.direction = "left"
            }

            override fun onSwipeRight() {
                Snake.alive = true
                if (Snake.direction != "left")
                    Snake.direction = "right"
            }

            override fun onSwipeTop() {
                Snake.alive = true
                if (Snake.direction != "up")
                    Snake.direction = "down"
            }
            override fun onSwipeBottom() {
                Snake.alive = true
                if (Snake.direction != "down")
                    Snake.direction = "up"
            }
        })

        // move the snake
        CoroutineScope(Dispatchers.IO).launch {
            while (true) {
                while (Snake.alive) {
                    when (Snake.direction) {
                        "up" -> {
                            println("move up")
                            // create new head position
                            Snake.headY -= 1
                            if (Snake.headY < 0) Snake.headY += Game.boardSize

                        }
                        "down" -> {
                            println("move down")
                            // create new head position
                            Snake.headY += 1
                            if (Snake.headY >= Game.boardSize) Snake.headY -= Game.boardSize

                        }
                        "left" -> {
                            println("move left")
                            // create new head position
                            Snake.headX -= 1
                            if (Snake.headX < 0) Snake.headX += Game.boardSize


                        }
                        "right" -> {
                            println("move right")
                            // create new head position
                            Snake.headX += 1
                            if (Snake.headX >= Game.boardSize) Snake.headX -= Game.boardSize

                        }

                    }
                    if (game.collided()) {
                        Snake.alive = false
                        game.reset()
                    }
                    else {
                        // convert head to body
                        Snake.bodyParts.add(Pair(Snake.headX, Snake.headY))

                        if (game.eaten()) {
                            game.generateFood()
                        }
                        else
                            Snake.bodyParts.removeAt(0)
                    }



                    //game speed in millisecond
                    canvas.invalidate()
                    delay(300)
                }
            }
        }

        binding.buttonUp.setOnClickListener {
            Snake.alive = true
            if (Snake.direction != "down")
                Snake.direction = "up"
        }
        binding.buttonDown.setOnClickListener {
            Snake.alive = true
            if (Snake.direction != "up")
                Snake.direction = "down"
        }
        binding.buttonLeft.setOnClickListener {
            Snake.alive = true
            if (Snake.direction != "right")
                Snake.direction = "left"
        }
        binding.buttonRight.setOnClickListener {
            Snake.alive = true
            if (Snake.direction != "left")
                Snake.direction = "right"
        }
    }
}