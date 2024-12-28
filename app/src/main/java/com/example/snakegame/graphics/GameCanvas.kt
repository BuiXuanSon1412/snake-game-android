package com.example.snakegame.graphics

import android.annotation.SuppressLint

import android.util.AttributeSet
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Handler
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import com.example.snakegame.objects.Food
import com.example.snakegame.objects.Game

import com.example.snakegame.objects.Snake
import kotlin.math.abs

class GameCanvas @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MapCanvas(context, attrs, defStyleAttr) {
    var game = Game(0)
    var gameReady = false;
    init {
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
        setOnTouchListener(object : OnSwipeTouchListener() {

            override fun onSwipeLeft() {
                game.state = true
                if (game.snake.direction != "right")
                    game.snake.direction = "left"
            }

            override fun onSwipeRight() {
                game.state = true
                if (game.snake.direction != "left")
                    game.snake.direction = "right"
            }

            override fun onSwipeTop() {
                game.state = true
                if (game.snake.direction != "up")
                    game.snake.direction = "down"
            }
            override fun onSwipeBottom() {
                game.state = true
                if (game.snake.direction != "down")
                    game.snake.direction = "up"
            }
        })
    }
    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        if (gameReady) {
            super.onDraw(canvas)
            println("Snake: ${game.snake.headX} ${game.snake.headY}")
            println("Food: ${game.food.posX} ${game.food.posY}")
            println("draw map index: ${mapIndex}")
            drawInstance(canvas)
        }
    }
    override fun performClick(): Boolean {
        // Notify accessibility services that a click event occurred
        super.performClick()
        return false
    }


    private fun drawInstance(canvas: Canvas) {
        val snake = Paint()
        snake.color = Color.GREEN

        for (i in game.snake.bodyParts){
            canvas.drawRect(
                pixelSize!! * i.first,
                pixelSize!! * i.second,
                pixelSize!! * i.first+ pixelSize!!,
                pixelSize!! * i.second+ pixelSize!!,
                snake)
        }

        val food = Paint()
        food.color = Color.RED
        canvas.drawRect(
            pixelSize!! * game.food.posX,
            pixelSize!! * game.food.posY,
            pixelSize!! * game.food.posX + pixelSize!!,
            pixelSize!! * game.food.posY + pixelSize!!,
            food)
    }
    fun updateAnimation() {
        game.snake.move()
        println("${game.snake.headX} ${game.snake.headY}")
        if (game.checkCollided()) {
            game.state = false
            game.reset()
        }
        else {
            // convert head to body
            game.snake.bodyParts.add(Pair(game.snake.headX, game.snake.headY))
            if (game.checkEaten()) {
                game.generateFood()
            }
            else
                game.snake.bodyParts.removeAt(0)
        }
    }

}