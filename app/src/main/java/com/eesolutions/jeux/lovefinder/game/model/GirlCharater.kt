package com.eesolutions.jeux.lovefinder.game.model

import com.eesolutions.jeux.lovefinder.R

private const val GO_NORMAL = 1
private const val GO_HOME = 2
private const val AT_HOME = 3
const val VELOCITY_HOME = 0.5f
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
    // all states must be declared here
    var state = GO_NORMAL

    private var lastChangeDirectionNanoTime: Long = -1
    private var lastTimeAtHomeNanoTime: Long = -1
    init {
        topToBottoms = mutableListOf<Int>(R.drawable.girl00, R.drawable.girl01, R.drawable.girl02)
        rightToLefts = mutableListOf<Int>(R.drawable.girl10, R.drawable.girl11, R.drawable.girl12)
        leftToRights = mutableListOf<Int>(R.drawable.girl20, R.drawable.girl21, R.drawable.girl22)
        bottomToTops = mutableListOf<Int>(R.drawable.girl30, R.drawable.girl31, R.drawable.girl32)
    }

    override fun moveOneStep () {
        // Current time in nanoseconds
        val now = System.nanoTime()
        when (state) {
            GO_HOME -> {
                if ((x > surfaceWidth - CORNER_RADIUS - objectWidth - 10) && (y > surfaceHeight - CORNER_RADIUS - objectWidth - 10) ) { // arrive home
                    state = AT_HOME
                    visibility = false
                    lastTimeAtHomeNanoTime = now
                }
                else {
                    // compute direction
                    movingVectorX = surfaceWidth - x
                    movingVectorY = surfaceHeight - y
                    VELOCITY = VELOCITY_HOME

                    // call super
                    super.moveOneStep()
                }
            }
            AT_HOME -> {
                // Current time in nanoseconds
                val now = System.nanoTime()
                val deltaTime = ((now - lastTimeAtHomeNanoTime) / 1000000).toInt()
                if (deltaTime/1000 > (3..5).random()) {
                    state = GO_NORMAL
                    visibility = true
                    VELOCITY = VELOCITY_NORMAL
                    x = surfaceWidth/2
                    y = surfaceHeight/2
                    movingVectorX = (-20..20).random()
                    movingVectorY = (-15..15).random()
                }
            }
            else -> { // GO_NORMAL
                // normal step => random change direction
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

    }

    fun goHome() {
        state = GO_HOME
    }

    fun isVisible() : Boolean {
        return visibility
    }

}