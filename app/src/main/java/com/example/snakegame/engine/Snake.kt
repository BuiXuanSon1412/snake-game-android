package com.example.snakegame.engine

data class Snake(var headX: Float = 0f, var headY: Float = 0f) {
    var bodyParts = mutableListOf<Pair<Float, Float>>() // first: X, second: Y
}