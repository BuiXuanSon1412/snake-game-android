package com.example.snakegame.graphic

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.example.snakegame.`object`.Food
import com.example.snakegame.`object`.Map
import com.example.snakegame.`object`.Snake

open class MapCanvas @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var pixelSize: Float = 0f
    var mapIndex: Int = 0
    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        pixelSize = this.width / 21f
        // set color's objects
        val background = Paint()
        background.color = Color.DKGRAY
        // draw background
        canvas.drawRect(0f,0f,315f,315f,background)
        // draw map
        drawWall(canvas, mapIndex)
    }
    override fun performClick(): Boolean {
        // Notify accessibility services that a click event occurred
        super.performClick()
        return true
    }
    protected fun drawWall(canvas: Canvas, index: Int) {
        val map = Map.mapMatrix[index]
        val wall = Paint()
        wall.color = Color.BLACK
        for (row in map.indices) {
            for (col in map[row].indices) {
                if (map[row][col] == 1) {
                    canvas.drawRect(
                        col * pixelSize,
                        row * pixelSize,
                        col * pixelSize + pixelSize,
                        row * pixelSize + pixelSize, wall)
                }
            }
        }
    }
}