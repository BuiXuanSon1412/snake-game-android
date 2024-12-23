package com.example.snakegame.objects

class Snake {
    companion object {
        // default: just one body part
        var headX = 0.0f
        var headY = 0.0f
        var bodyParts = mutableListOf(arrayOf(0f, 0f))
        var direction = "right";
        var alive = false;

        fun reset() {
            headX = 0f;
            headY = 0f;
            bodyParts = mutableListOf(arrayOf(0f, 0f))
            direction = "right";

        }
    }
}