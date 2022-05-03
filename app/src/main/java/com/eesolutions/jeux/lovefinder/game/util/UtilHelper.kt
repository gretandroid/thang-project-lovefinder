package com.eesolutions.jeux.lovefinder.game.util

object UtilHelper {

    fun hashCodeOf(a: Int, b: Int) : Int {
        var hash = 23
        hash = hash * 31 + a
        hash = hash * 31 + b
        return hash
    }
}