package com.example.imagesource.utils

import com.example.imagesource.utils.constants.Directions

class FlipRotateCounter {
    val rotateDirections = listOf(
        Directions.UP,
        Directions.RIGHT,
        Directions.DOWN,
        Directions.LEFT
    )

    var direction = rotateDirections[0]
        private set

    private var rotateCount = 0

    var isFlipped = false
        private set

    fun updateFlipCount() {
        isFlipped = !isFlipped
        rotateCount += rotateDirections.size / 2
    }

    fun updateRotateCount() {
        rotateCount++
        resetRotateCount()
        direction = rotateDirections[rotateCount]
    }

    private fun resetRotateCount() {
        if (rotateCount >= rotateDirections.size) rotateCount %= rotateDirections.size
    }
}