package com.example.snakegame.objects

class Snake {
    var headX = 0
    var headY = 0
    var bodyParts = mutableListOf<Pair<Int, Int>>() // first: X, second: Y
    var direction = "right";

    fun move() {
        when (direction) {
            "up" -> {
                println("move up")
                // create new head position
                headY -= 1
                if (headY < 0) headY += Game.boardSize

            }
            "down" -> {
                println("move down")
                // create new head position
                headY += 1
                if (headY >= Game.boardSize) headY -= Game.boardSize

            }
            "left" -> {
                println("move left")
                // create new head position
                headX -= 1
                if (headX < 0) headX += Game.boardSize


            }
            "right" -> {
                println("move right")
                // create new head position
                headX += 1
                if (headX >= Game.boardSize) headX -= Game.boardSize

            }

        }
    }

}