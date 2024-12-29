package com.example.snakegame.engine

class Snake {
    var headX = 0f
    var headY = 0f
    var bodyParts = mutableListOf<Pair<Float, Float>>() // first: X, second: Y
    var step = 0.025f
    //var direction = "right";

    fun move(direction: String) {
        when (direction) {
            "up" -> {
                println("move up")
                // create new head position
                headY -= step
                if (headY < 0) headY = 20f
            }
            "down" -> {
                println("move down")
                // create new head position
                headY += step
                if (headY >= Game.boardSize) headY = 0f

            }
            "left" -> {
                println("move left")
                // create new head position
                headX -= step
                if (headX < 0) headX = 20f


            }
            "right" -> {
                println("move right")
                // create new head position
                headX += step
                if (headX >= Game.boardSize) headX = 0f

            }

        }
    }

}