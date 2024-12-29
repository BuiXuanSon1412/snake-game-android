package com.example.snakegame.animations

import android.annotation.SuppressLint

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import com.example.snakegame.engine.Game

import kotlin.math.abs

@SuppressLint("ViewConstructor")
class GameView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    private val game: Game
) : MapView(context, attrs, defStyleAttr) {
    //var gameReady = false;
    init {
        // Add a pre-draw listener to the ViewTreeObserver
        viewTreeObserver.addOnPreDrawListener {
            pixelSize = this.width / Game.boardSize.toFloat()
            true
        }
    }

    init {

    }
    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        mapIndex = game.mapIndex
        super.onDraw(canvas)
        //println("Snake: ${game.snake.headX} ${game.snake.headY}")
        //println("Food: ${game.food.posX} ${game.food.posY}")
        //println("draw map index: ${mapIndex}")
        drawInstances(canvas)

    }
    override fun performClick(): Boolean {
        // Notify accessibility services that a click event occurred
        super.performClick()
        return false
    }


    private fun drawInstances(canvas: Canvas) {
        val snake = Paint()
        snake.color = Color.GREEN

        for (i in game.snake.bodyParts){
            canvas.drawRect(
                pixelSize * i.first,
                pixelSize * i.second,
                pixelSize * i.first+ pixelSize,
                pixelSize * i.second+ pixelSize,
                snake)
        }

        val food = Paint()
        food.color = Color.RED
        canvas.drawRect(
            pixelSize * game.food.posX,
            pixelSize * game.food.posY,
            pixelSize * game.food.posX + pixelSize,
            pixelSize * game.food.posY + pixelSize,
            food)
    }
    fun updateAnimation() {
        if (game.direction != null) {
            game.snake.move(game.direction!!)

            game.snake.bodyParts.add(Pair(game.snake.headX, game.snake.headY))
            if (game.checkEaten()) {
                game.generateFood()
            } else
                game.snake.bodyParts.removeAt(0)

        }
    }

}