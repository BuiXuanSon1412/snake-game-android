package com.example.snakegame.utils

import android.content.Context

object ScoreManager {
    private const val PREFS_NAME = "HighScorePrefs"
    private const val HIGH_SCORE_KEY = "HIGH_SCORE"

    fun saveHighScore(context: Context, highScore: Int) {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit().putInt(HIGH_SCORE_KEY, highScore).apply()
    }

    fun getHighScore(context: Context): Int {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getInt(HIGH_SCORE_KEY, 0)
    }
}