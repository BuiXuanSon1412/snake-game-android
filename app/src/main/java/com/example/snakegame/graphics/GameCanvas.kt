package com.example.snakegame.graphics

import android.annotation.SuppressLint

import android.util.AttributeSet
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.example.snakegame.objects.Food

import com.example.snakegame.objects.Snake

class GameCanvas @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MapCanvas(context, attrs, defStyleAttr) {
    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
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
            canvas.drawRect(i[0], i[1] , i[0] + pixelSize!!, i[1] + pixelSize!!, snake)
        }

        val food = Paint()
        food.color = Color.RED
        canvas.drawRect(Food.posX, Food.posY, Food.posX + pixelSize!!, Food.posY + pixelSize!!,food)

    }

}