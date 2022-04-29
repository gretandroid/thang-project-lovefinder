package com.eesolutions.jeux.lovefinder.game.model

import android.graphics.Bitmap

abstract class ImageObject(
    val image: Bitmap,
    val rowCount: Int,
    val colCount: Int,
    val x: Int,
    val y: Int ) {

    var imageWidth : Int = 0
    var imageHeight : Int = 0

    var objectWidth : Int = 0
    var objectHeight : Int = 0

    init {
        imageWidth = image.width
        imageHeight = image.height

        objectWidth = imageWidth / colCount
        objectHeight = imageHeight / rowCount
    }

    protected fun cropSubImage(row: Int, col: Int) : Bitmap {
        return Bitmap.createBitmap(image, col*objectWidth, row*objectHeight, objectWidth, objectHeight)
    }

}