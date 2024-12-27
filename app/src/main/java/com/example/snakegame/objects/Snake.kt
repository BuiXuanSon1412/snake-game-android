package com.example.snakegame.objects

class Snake {
    companion object {
        // default: just one body part
        var headX = 0
        var headY = 0
        var bodyParts = mutableListOf<Pair<Int, Int>>() // first: X, second: Y
        var direction = "right";
        var alive = false;
    }
}