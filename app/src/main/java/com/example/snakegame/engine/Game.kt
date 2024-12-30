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
    //var updateDirection: String? = null
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
        score = 0
        direction = null
        //updateDirection = null
        initSnake()
        generateFood()
    }

    private fun initSnake() {
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

    // process when snake turns
    fun turn (updateDirection: String) {
        val y = snake.headY.toInt()
        val x = snake.headX.toInt()

        if (updateDirection == "up" || updateDirection == "down") {
            if (direction == "right") {
                if (snake.headX - x > 0f) snake.headX = x + 1f
                //else snake.headX = x.toFloat()
            }
            else if (direction == "left") {
                if (snake.headX - x > 0f) snake.headX = x.toFloat()
                //else snake.headX = x + 1f
            }
        } else {
            if (direction == "down") {
                if (snake.headY - y > 0f) snake.headY = y + 1f
                //else snake.headY = y.toFloat()
            }
            else if (direction == "up") {
                if (snake.headY - y > 0f) snake.headY = y.toFloat()
                //else snake.headY = y+1f
            }
        }
        direction = updateDirection

    }

    fun checkWallCollision(): Boolean {
        return map[snake.headY.toInt()][snake.headX.toInt()] == 1
    }

    fun checkEaten(): Boolean {
        if (snake.headX.toInt() == food.posX && snake.headY.toInt() == food.posY) {
            //_score.value = _score.value?.plus(20)
            return true
        }
        return false
    }


}