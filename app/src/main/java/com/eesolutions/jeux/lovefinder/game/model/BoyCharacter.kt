package com.eesolutions.jeux.lovefinder.game.model

import android.graphics.Bitmap
import android.graphics.Canvas
import com.eesolutions.jeux.lovefinder.R
import java.lang.Math.abs

class BoyCharacter(
    surfaceWidth: Int,
    surfaceHeight: Int,
    objectWidth: Int,
    objectHeight: Int,
    x: Int,
    y: Int
) : Charater(
    surfaceWidth,
    surfaceHeight,
    objectWidth,
    objectHeight,
    x,
    y
) {

    init {
        topToBottoms = mutableListOf<Int>(R.drawable.boy00, R.drawable.boy01, R.drawable.boy02)
        rightToLefts = mutableListOf<Int>(R.drawable.boy10, R.drawable.boy11, R.drawable.boy12)
        leftToRights = mutableListOf<Int>(R.drawable.boy20, R.drawable.boy21, R.drawable.boy22)
        bottomToTops = mutableListOf<Int>(R.drawable.boy30, R.drawable.boy31, R.drawable.boy32)
    }

    fun findMatch(girls : List<GirlCharater>) : List<GirlCharater> {
        val perimeter = 15 // pixel
        var result = girls.filter {
            kotlin.math.abs(x - it.x) < perimeter
                    && kotlin.math.abs(y - it.y) < perimeter}
        return result
    }


}