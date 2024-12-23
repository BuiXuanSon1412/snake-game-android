package com.example.snakegame.graphics

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.example.snakegame.objects.Game
import com.example.snakegame.objects.Map

open class MapCanvas @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    protected var pixelSize: Float? = null
    private var border: Boolean = false
    var mapIndex: Int = 0
    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        pixelSize = this.width / Game.boardSize.toFloat()
        canvas.drawColor(Color.GRAY)
        // draw map
        drawWall(canvas, mapIndex)
        if (border) {
            val borderPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                color = Color.YELLOW // Border color
                style = Paint.Style.STROKE
                strokeWidth = 10f // Border thickness
            }
            canvas.drawRect(0f, 0f, width * 1f, height * 1f, borderPaint)
        }
    }

    override fun performClick(): Boolean {
        // Notify accessibility services that a click event occurred
        super.performClick()
        return false
    }

    private fun drawWall(canvas: Canvas, index: Int) {
        val map = Map.mapMatrix[index]
        val wall = Paint()
        wall.color = Color.BLACK
        for (row in map.indices) {
            for (col in map[row].indices) {
                if (map[row][col] == 1) {
                    canvas.drawRect(
                        col * pixelSize!!,
                        row * pixelSize!!,
                        col * pixelSize!! + pixelSize!!,
                        row * pixelSize!! + pixelSize!!, wall)
                }
            }
        }
    }
    public fun changeSelectingState(state: Boolean) {
        border = state
        invalidate()
    }


}