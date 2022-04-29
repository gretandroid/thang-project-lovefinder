package com.eesolutions.jeux.lovefinder.game.model

import android.graphics.Bitmap

class BoyCharacter(
    image: Bitmap,
    x: Int,
    y: Int
) : ImageObject(image, 4, 3, x, y) {

    companion object {
        private const val ROW_TOP_TO_BOTTOM = 0
        private const val ROW_RIGHT_TO_LEFT = 1
        private const val ROW_LEFT_TO_RIGHT = 2
        private const val ROW_BOTTOM_TO_TOP = 3
    }

    private val currentRow = ROW_LEFT_TO_RIGHT
    private val currentCol = 0

    private val topToBottoms = mutableListOf<Bitmap>()
    private val rightToLefts = mutableListOf<Bitmap>()
    private val leftToRights = mutableListOf<Bitmap>()
    private val bottomToTops = mutableListOf<Bitmap>()

    init {
        for (col in (1..colCount)) {
            topToBottoms[col - 1] = cropSubImage(ROW_TOP_TO_BOTTOM, col - 1)
            rightToLefts[col - 1] = cropSubImage(ROW_RIGHT_TO_LEFT, col - 1)
            leftToRights[col - 1] = cropSubImage(ROW_LEFT_TO_RIGHT, col - 1)
            bottomToTops[col - 1] = cropSubImage(ROW_BOTTOM_TO_TOP, col - 1)
        }
    }
}