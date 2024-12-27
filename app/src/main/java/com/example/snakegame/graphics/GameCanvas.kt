package com.example.snakegame.graphics

import android.annotation.SuppressLint

import android.util.AttributeSet
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.example.snakegame.objects.Food
import com.example.snakegame.objects.Game

import com.example.snakegame.objects.Snake

class GameCanvas @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MapCanvas(context, attrs, defStyleAttr) {
    //var game: Game = Game(mapIndex, this.width, this.height)

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        println("Snake: ${Snake.headX} ${Snake.headY}")
        println("Food: ${Food.posX} ${Food.posY}")
        println("map index: ${mapIndex}")
        drawInstance(canvas)
    }
    override fun performClick(): Boolean {
        // Notify accessibility services that a click event occurred
        super.performClick()
        return false
    }

    private fun drawInstance(canvas: Canvas) {
        val snake = Paint()
        snake.color = Color.GREEN

        for (i in Snake.bodyParts){
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
            pixelSize!! * Food.posX,
            pixelSize!! * Food.posY,
            pixelSize!! * Food.posX + pixelSize!!,
            pixelSize!! * Food.posY + pixelSize!!,
            food)

    }

}