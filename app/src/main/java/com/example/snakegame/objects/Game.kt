package com.example.snakegame.objects



class Game (private var mapIndex :Int, private val widthMap: Int, val heightMap: Int) {
    private var state: Boolean = false
    companion object{
        val boardSize: Int = 21
    }
    //val pixelSize: Float = widthMap/boardSize.toFloat()
    private var map = Map.mapMatrix[mapIndex]
    var score: Int = 0

    init {
        for (row in map) {
            for (i in row) {
                print("$i ")
            }
            println()
        }
        reset()
    }

    fun reset() {
        initSnake()
        generateFood()
    }
    fun gameState(): Boolean {
        return state
    }
    fun initSnake() {
        var pixelX = 0
        var pixelY = 0
        do {
            pixelX = (0..20).random()
            pixelY = (0..20).random()
        } while (map[pixelY][pixelX] == 1)
        Snake.headX = pixelX
        Snake.headY = pixelY
        Snake.bodyParts.add(Pair(Snake.headX, Snake.headY))
    }

    fun generateFood() {
        var pixelX = 0
        var pixelY = 0
        do {
            pixelX = (0..20).random()
            pixelY = (0..20).random()
        } while (map[pixelY][pixelX] == 1)
        Food.posX = pixelX
        Food.posY = pixelY
    }


    fun collided(): Boolean {
        val map = Map.mapMatrix[this.mapIndex]
        val i = map[Snake.headY][Snake.headX]
        if (i == 1) {
            println("${Snake.headX} ${Snake.headY} ${i}")
        }
        return map[Snake.headY][Snake.headX] == 1
    }

    fun eaten(): Boolean {
        if (Snake.headX == Food.posX && Snake.headY == Food.posY) {
            score += 20
            return true
        }
        return false
    }

}