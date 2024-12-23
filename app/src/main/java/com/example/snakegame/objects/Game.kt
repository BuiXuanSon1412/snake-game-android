package com.example.snakegame.objects

import com.example.snakegame.objects.Food.Companion.posX
import com.example.snakegame.objects.Food.Companion.posY

class Game (private var mapIndex :Int, private val widthMap: Int, val heightMap: Int) {
    private var state: Boolean = false
    companion object{
        val boardSize: Int = 21
    }
    val pixelSize: Float = widthMap/boardSize.toFloat()
    private var map = Map.mapMatrix[mapIndex]
    var score: Int = 0

    //init {
    //    initSnake()
    //    generateFood()
    //}

    fun reset() {

    }
    fun gameState(): Boolean {
        return state
    }
    fun initSnake() {
        var pixelX = 0
        var pixelY = 0
        while (map[pixelX][pixelY] == 1) {
            pixelX = (0..20).random()
            pixelY = (0..20).random()
        }
        Snake.headX = pixelX * pixelSize
        Snake.headY = pixelY * pixelSize
        Snake.bodyParts.add(arrayOf(Snake.headX, Snake.headY))
    }

    fun generateFood() {
        var pixelX = 0
        var pixelY = 0
        while (map[pixelX][pixelY] == 1) {
            pixelX = (0..20).random()
            pixelY = (0..20).random()
        }
        Food.posX = pixelX * pixelSize
        Food.posY = pixelY * pixelSize
    }


    fun collided(): Boolean {
        val map = Map.mapMatrix[this.mapIndex]
        for (row in map.indices) {
            for (col in map[row].indices) {
                val pixelY = pixelSize * row.toFloat()
                val pixelX = pixelSize * col.toFloat()
                if (map[row][col] == 1 && Snake.headX == pixelX && Snake.headY == pixelY) return true;
            }
        }
        return false
    }

    fun eaten(): Boolean {
        if (Snake.headX == Food.posX && Snake.headY == Food.posY) {
            score += 20
            return true
        }
        return false
    }

}