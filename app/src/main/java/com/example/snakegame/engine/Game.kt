package com.example.snakegame.engine


class Game (var mapIndex: Int) {
    companion object{
        val boardSize: Int = 21
    }
    // state of the current game
    var state: Int = 0          // 0: initial, 1: in-game, 2: pause, 3: end
    private var map = Map.mapMatrix[mapIndex]

    var score = 0
    // objects of the game
    lateinit var snake: Snake
    lateinit var food: Food
    var direction: String? = null
    init {
        //for (row in map) {
        //    for (i in row) {
        //        print("$i ")
        //    }
        //    println()
        //}
        //_score.value = 0
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
        snake.headX = pixelX.toFloat()
        snake.headY = pixelY.toFloat()
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
        val y = snake.headY.toInt()
        val x = snake.headX.toInt()
        if (direction == "up") {
            if (snake.headX - x < 0.75f && y-1 >= 0 &&  map[y-1][x] == 1) return true
            if (snake.headX - x > 0.75f && y-1 >= 0 && map[y-1][x+1] == 1) return true
        } else if (direction == "down") {
            if (snake.headX - x < 0.75f && y+1 < 21 && map[y+1][x] == 1) return true
            if (snake.headX - x > 0.75f && x+1 < 21 && y+1 < 21 && map[y+1][x+1] == 1) return true
        } else if (direction == "right") {
            if (snake.headY - y < 0.75f && x+1 < 21 && map[y][x+1] == 1) return true
            if (snake.headY - y > 0.75f && x+1 < 21 && y+1 < 21 && map[y+1][x+1] == 1) return true
        } else if (direction == "left") {
            if (snake.headY - y < 0.75f && x-1 >= 0 && map[y][x-1] == 1) return true
            if (snake.headY - y > 0.75f && y+1 < 21 && x-1 >= 0 && map[y+1][x-1] == 1) return true
        }
        return false
    }

    fun checkEaten(): Boolean {
        if (snake.headX.toInt() == food.posX && snake.headY.toInt() == food.posY) {
            //_score.value = _score.value?.plus(20)
            return true
        }
        return false
    }


}