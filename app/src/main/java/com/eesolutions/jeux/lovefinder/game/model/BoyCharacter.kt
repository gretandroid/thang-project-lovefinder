package com.eesolutions.jeux.lovefinder.game.model

import android.graphics.Bitmap
import android.graphics.Canvas

class BoyCharacter(
    val surfaceWidth: Int,
    val surfaceHeight: Int,
    val objectWidth : Int,
    val objectHeight : Int,
//    image: Bitmap,
    x: Int,
    y: Int
) : ImageObject(
//    image,
    4,
    3,
    x,
    y) {

    companion object {
        private const val ROW_TOP_TO_BOTTOM = 0
        private const val ROW_RIGHT_TO_LEFT = 1
        private const val ROW_LEFT_TO_RIGHT = 2
        private const val ROW_BOTTOM_TO_TOP = 3
    }

    private var currentRow = ROW_LEFT_TO_RIGHT
    private var currentCol = 0

    private val topToBottoms = mutableListOf<Bitmap>()
    private val rightToLefts = mutableListOf<Bitmap>()
    private val leftToRights = mutableListOf<Bitmap>()
    private val bottomToTops = mutableListOf<Bitmap>()


    // Velocity of character (pixel/millisecond)
    val VELOCITY = 0.1f

    var movingVectorX = 10
    var movingVectorY = 5

    private var lastDrawNanoTime: Long = -1

    init {
//        for (col in (1..colCount)) {
//            topToBottoms[col - 1] = cropSubImage(ROW_TOP_TO_BOTTOM, col - 1)
//            rightToLefts[col - 1] = cropSubImage(ROW_RIGHT_TO_LEFT, col - 1)
//            leftToRights[col - 1] = cropSubImage(ROW_LEFT_TO_RIGHT, col - 1)
//            bottomToTops[col - 1] = cropSubImage(ROW_BOTTOM_TO_TOP, col - 1)
//        }
    }

    fun inverseX () {
        movingVectorX *= -1
    }

    fun inverseY () {
        movingVectorY *= -1
    }
    private fun getCurrentImage() : Bitmap {
        when (currentRow) {
            ROW_TOP_TO_BOTTOM -> return topToBottoms[currentCol]
            ROW_RIGHT_TO_LEFT -> return rightToLefts[currentCol]
            ROW_LEFT_TO_RIGHT -> return leftToRights[currentCol]
            ROW_BOTTOM_TO_TOP -> return bottomToTops[currentCol]
            else -> return topToBottoms[currentCol]
        }
    }

    public fun move() {
        x = x + 20
        y = y + 20
    }

    public fun moveOneStep() {
        this.currentCol++
        if (currentCol >= colCount) {
            this.currentCol = 0
        }

        // Current time in nanoseconds
        val now = System.nanoTime()


        // Never once did draw.
        if (lastDrawNanoTime == -1L) {
            lastDrawNanoTime = now
        }

        // Change nanoseconds to milliseconds (1 nanosecond = 1000000 milliseconds).
        val deltaTime = ((now - lastDrawNanoTime) / 1000000).toInt()
//        val deltaTime = 100

        // Distance moves
        val distance = VELOCITY * deltaTime

        val movingVectorLength =
            Math.sqrt((movingVectorX * movingVectorX + movingVectorY * movingVectorY).toDouble())


        // Calculate the new position of the game character.
        x = x + (distance * movingVectorX / movingVectorLength).toInt()
        y = y + (distance * movingVectorY / movingVectorLength).toInt()


        // When the game's character touches the edge of the screen, then change direction
        if (x < 0) {
            x = 0
            movingVectorX = -movingVectorX
        } else if (x > surfaceWidth - objectWidth) {
            x = surfaceWidth - objectWidth
            movingVectorX = -movingVectorX
        }

        if (y < 0) {
            y = 0
            movingVectorY = -movingVectorY
        } else if (y > surfaceHeight - objectHeight) {
            y = surfaceHeight - objectHeight
            movingVectorY = -movingVectorY
        }

        // rowUsing

        // rowUsing
        if (movingVectorX > 0) {
            if (movingVectorY > 0 && Math.abs(movingVectorX) < Math.abs(movingVectorY)) {
                this.currentRow = ROW_TOP_TO_BOTTOM
            } else if (movingVectorY < 0 && Math.abs(movingVectorX) < Math.abs(movingVectorY)) {
                this.currentRow = ROW_BOTTOM_TO_TOP
            } else {
                this.currentRow = ROW_LEFT_TO_RIGHT
            }
        } else {
            if (movingVectorY > 0 && Math.abs(movingVectorX) < Math.abs(movingVectorY)) {
                this.currentRow = ROW_TOP_TO_BOTTOM
            } else if (movingVectorY < 0 && Math.abs(movingVectorX) < Math.abs(movingVectorY)) {
                this.currentRow = ROW_BOTTOM_TO_TOP
            } else {
                this.currentRow = ROW_RIGHT_TO_LEFT
            }
        }
    }

    fun draw(canvas: Canvas?) {
//        val image: Bitmap = this.getCurrentImage()
//        canvas.drawBitmap(image, x.toFloat(), y.toFloat(), null)
        // Last draw time.
        lastDrawNanoTime = System.nanoTime()
    }

    fun setMovingVector(movingVectorX: Int, movingVectorY: Int) {
        this.movingVectorX = movingVectorX
        this.movingVectorY = movingVectorY
    }
}