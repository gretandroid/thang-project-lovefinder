package com.eesolutions.jeux.lovefinder.game.model

import com.eesolutions.jeux.lovefinder.R

class GirlCharater(
    surfaceWidth: Int,
    surfaceHeight: Int,
    objectWidth: Int,
    objectHeight: Int,
    x: Int,
    y: Int,
    movingVectorX : Int,
    movingVectorY : Int
) : Charater(
    surfaceWidth,
    surfaceHeight,
    objectWidth,
    objectHeight,
    x,
    y,
    movingVectorX,
    movingVectorY
) {

    private var lastChangeDirectionNanoTime: Long = -1
    init {
        topToBottoms = mutableListOf<Int>(R.drawable.girl00, R.drawable.girl01, R.drawable.girl02)
        rightToLefts = mutableListOf<Int>(R.drawable.girl10, R.drawable.girl11, R.drawable.girl12)
        leftToRights = mutableListOf<Int>(R.drawable.girl20, R.drawable.girl21, R.drawable.girl22)
        bottomToTops = mutableListOf<Int>(R.drawable.girl30, R.drawable.girl31, R.drawable.girl32)
    }

    override fun moveOneStep () {
        // Current time in nanoseconds
        val now = System.nanoTime()

        if (lastChangeDirectionNanoTime == -1L) {
            lastChangeDirectionNanoTime = now
        }

        val deltaTime = ((now - lastChangeDirectionNanoTime) / 1000000).toInt()
        if (deltaTime/1000 > (3..7).random()) {
            // change direction automatique
                movingVectorX = (-20..20).random()
            movingVectorY = (-15..15).random()
            lastChangeDirectionNanoTime = now
        }


        // call super
        super.moveOneStep()
    }

}