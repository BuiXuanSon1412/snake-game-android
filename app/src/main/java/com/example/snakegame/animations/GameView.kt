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
    // state of animation
    // waiting: before player starts to play the game
    // idle: user pauses the game
    // running: in-game
    var state = "waiting";
    private var gameOverListener: GameOverListener? = null

    init {
        // Add a pre-draw listener to the ViewTreeObserver
        viewTreeObserver.addOnPreDrawListener {
            pixelSize = this.width / Game.boardSize.toFloat()
            true
        }
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        mapIndex = game.mapIndex
        super.onDraw(canvas)
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
            canvas.drawRoundRect(
                pixelSize * i.first,
                pixelSize * i.second,
                pixelSize * i.first+ pixelSize,
                pixelSize * i.second+ pixelSize,
                pixelSize / 3f, pixelSize / 3f,
                snake)
        }

        val food = Paint()
        food.color = Color.RED
        canvas.drawRoundRect(
            pixelSize * game.food.posX,
            pixelSize * game.food.posY,
            pixelSize * game.food.posX + pixelSize,
            pixelSize * game.food.posY + pixelSize,
            pixelSize / 2f, pixelSize / 2f,
            food)
    }
    // return true if need to render
    // return false if no need to render
    fun updateAnimation() : Boolean{
        if (state == "running") {
            game.turn()
            game.move()
            if (!game.checkWallCollision() && !game.checkSelfCollision()) {
                game.snake.bodyParts.add(Pair(game.snake.headX , game.snake.headY))
                if (game.checkFoodEaten()) {
                    game.spawnFood()
                }
                else game.snake.bodyParts.removeAt(0)
                return true
            }
            else {
                //saveHighScore(this, game.score)
                gameOver()
                state = "idle"
            }
        }
        return false
    }

    private fun saveHighScore(context: Context, highScore: Int) {
        val sharedPreferences = context.getSharedPreferences("HighScorePrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("HIGH_SCORE", highScore)
        editor.apply()
    }
    fun setGameOverListener(listener: GameOverListener) {
        gameOverListener = listener
    }

    private fun gameOver() {
        // Trigger the callback
        gameOverListener?.onGameOver()
    }
    interface GameOverListener {
        fun onGameOver()
    }
}