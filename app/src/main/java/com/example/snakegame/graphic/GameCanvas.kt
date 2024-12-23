package com.example.snakegame.graphic

import android.annotation.SuppressLint
import android.view.View
import android.util.AttributeSet
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.example.snakegame.`object`.Food
import com.example.snakegame.`object`.Map
import com.example.snakegame.`object`.Snake

class GameCanvas @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // set color's objects
        val snakeBody = Paint()
        snakeBody.color = Color.GREEN
        val food = Paint()
        food.color = Color.RED
        val background = Paint()
        background.color = Color.DKGRAY
        val wall = Paint()
        wall.color = Color.BLACK

        // draw background
        canvas.drawRect(0f,0f,1050f,1050f,background)

        // draw map
        val map = Map.mapMatrix[0]
        for (row in map.indices) {
            for (col in map[row].indices) {
                if (map[row][col] == 1) {
                    canvas.drawRect(col * 50f, row * 50f, col * 50f + 50f, row * 50f + 50f, wall)
                }
            }
        }
        // draw snake from array
        // left x, top y, right x+50, bottom y +50

        for (i in Snake.bodyParts){
            canvas.drawRect(i[0], i[1], i[0]+45, i[1]+45, snakeBody)
        }

        // draw food from array
        // left x, top y, right x+50, bottom y +50
        canvas.drawRect(Food.posX, Food.posY, Food.posX +45, Food.posY +45,food)


    }
    override fun performClick(): Boolean {
        // Notify accessibility services that a click event occurred
        super.performClick()
        return true
    }
}