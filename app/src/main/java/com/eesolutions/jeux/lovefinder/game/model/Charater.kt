package com.eesolutions.jeux.lovefinder.game.model

const val CORNER_RADIUS = 300 // dp

abstract class Charater(
    val surfaceWidth: Int,
    val surfaceHeight: Int,
    val objectWidth: Int,
    val objectHeight: Int,
    x: Int,
    y: Int,
    var movingVectorX: Int = 10,
    var movingVectorY: Int = 5
) : BaseObject(
    4,
    3,
    x,
    y
) {
    companion object {
        private const val ROW_TOP_TO_BOTTOM = 0
        private const val ROW_RIGHT_TO_LEFT = 1
        private const val ROW_LEFT_TO_RIGHT = 2
        private const val ROW_BOTTOM_TO_TOP = 3
    }


    protected lateinit var topToBottoms: MutableList<Int>
    protected lateinit var rightToLefts: MutableList<Int>
    protected lateinit var leftToRights: MutableList<Int>
    protected lateinit var bottomToTops: MutableList<Int>

    private var currentRow = ROW_LEFT_TO_RIGHT
    private var currentCol = 0


    // Velocity of character (pixel/millisecond)
    val VELOCITY = 0.1f

//    var movingVectorX = 10
//    var movingVectorY = 5

    private var lastDrawNanoTime: Long = -1

    public fun getCurrentImage(): Int {
        when (currentRow) {
            ROW_TOP_TO_BOTTOM -> return topToBottoms[currentCol]
            ROW_RIGHT_TO_LEFT -> return rightToLefts[currentCol]
            ROW_LEFT_TO_RIGHT -> return leftToRights[currentCol]
            ROW_BOTTOM_TO_TOP -> return bottomToTops[currentCol]
            else -> return topToBottoms[currentCol]
        }
    }

    public open fun moveOneStep() {
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

        // avoid corner which reserved to HOME and SCORE
        // RIGHT_BOTTOM
        if (x >  surfaceWidth - objectWidth - CORNER_RADIUS && y > surfaceHeight - objectHeight - CORNER_RADIUS) {
            x = surfaceWidth - objectWidth - CORNER_RADIUS
            y = surfaceHeight - objectHeight - CORNER_RADIUS
            movingVectorX = -movingVectorX
        }
        // LEFT_BOTTOM
        if (x < CORNER_RADIUS && y > surfaceHeight - objectHeight - CORNER_RADIUS) {
            x = CORNER_RADIUS
            y = surfaceHeight - objectHeight - CORNER_RADIUS
            movingVectorY = -movingVectorY
        }

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

    fun checkPoint() {
        // Last draw time.
        lastDrawNanoTime = System.nanoTime()
    }

    fun setMovingVector(movingVectorX: Int, movingVectorY: Int) {
        this.movingVectorX = movingVectorX
        this.movingVectorY = movingVectorY
    }

}