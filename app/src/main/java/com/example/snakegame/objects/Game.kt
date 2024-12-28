package com.example.snakegame.objects

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class Game (private var mapIndex: Int): ViewModel() {
    companion object{
        val boardSize: Int = 21
    }
    // state of the current game
    var state: Boolean = true
    private var map = Map.mapMatrix[mapIndex]

    // current game score
    private val _score = MutableLiveData<Int>()
    val score: LiveData<Int> get() = _score

    // objects of the game
    lateinit var snake: Snake
    lateinit var food: Food

    init {
        for (row in map) {
            for (i in row) {
                print("$i ")
            }
            println()
        }
        _score.value = 0
        reset()
    }

    fun reset() {
        initSnake()
        generateFood()
    }

    fun initSnake() {
        snake = Snake()
        var pixelX = 0
        var pixelY = 0
        do {
            pixelX = (0..20).random()
            pixelY = (0..20).random()
        } while (map[pixelY][pixelX] == 1)
        snake.headX = pixelX
        snake.headY = pixelY
        snake.bodyParts.add(Pair(snake.headX, snake.headY))
    }

    fun generateFood() {
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


    fun checkCollided(): Boolean {
        return map[snake.headY][snake.headX] == 1
    }

    fun checkEaten(): Boolean {
        if (snake.headX == food.posX && snake.headY == food.posY) {
            //_score.value = _score.value?.plus(20)
            return true
        }
        return false
    }

}