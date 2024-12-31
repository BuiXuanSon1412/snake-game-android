package com.example.snakegame.engine

import androidx.lifecycle.MutableLiveData


class Game (var mapIndex: Int) {
    companion object{
        val boardSize: Int = 21
    }
    // state of the current game
    private var map = Map.mapMatrix[mapIndex]
    var score = MutableLiveData<Int>()
    // objects of the game
    lateinit var snake: Snake
    lateinit var food: Food
    private val step = 1
    // direction of snake
    var direction: String? = null
    var redirection: String? = null
    init {
        reset()
    }

    fun reset() {
        score.value = 0
        direction = null
        //updateDirection = null
        releaseSnake()
        spawnFood()
    }

    private fun releaseSnake() {
        snake = Snake()
        var pixelX = 0
        var pixelY = 0
        do {
            pixelX = (0..20).random()
            pixelY = (0..20).random()
        } while (map[pixelY][pixelX] == 1)
        snake.headX = pixelX.toFloat()
        snake.headY = pixelY.toFloat()
        snake.bodyParts.add(Pair(snake.headX, snake.headY))
    }

    fun spawnFood() {
        food = Food()
        var pixelX = 0
        var pixelY = 0
        do {
            pixelX = (0..20).random()
            pixelY = (0..20).random()
        } while (map[pixelY][pixelX] == 1)
        food.posX = pixelX
        food.posY = pixelY
    }

    // process when snake turns
    fun turn () {
        direction = redirection
    }

    fun move() {
        when (direction) {
            "up" -> {
                snake.headY -= step
                if (snake.headY < 0) snake.headY = boardSize - 1f
            }
            "down" -> {
                snake.headY += step
                if (snake.headY >= boardSize) snake.headY = 0f
            }
            "left" -> {
                snake.headX -= step
                if (snake.headX < 0) snake.headX = boardSize - 1f
            }
            "right" -> {
                snake.headX += step
                if (snake.headX >= boardSize) snake.headX = 0f
            }
        }
    }
    fun checkWallCollision(): Boolean {
        return map[snake.headY.toInt()][snake.headX.toInt()] == 1
    }
    fun checkSelfCollision(): Boolean {
        for (i in snake.bodyParts) {
            if (snake.headX == i.first && snake.headY == i.second) return true
        }
        return false
    }

    fun checkFoodEaten(): Boolean {

        if (snake.headX.toInt() == food.posX && snake.headY.toInt() == food.posY) {
            val newScore = (score.value?: 0) + 20
            score.postValue(newScore)
            return true
        }
        return false
    }


}