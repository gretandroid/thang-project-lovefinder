package com.eesolutions.jeux.lovefinder.game.model

import com.eesolutions.jeux.lovefinder.R
import com.eesolutions.jeux.lovefinder.game.util.UtilHelper

class MatchObject (
    x: Int,
    y: Int
) : BaseObject(
    5,
    5,
    x,
    y
) {
    private var currentRow = 0
    private var currentCol = -1

    private var finish = true

    protected lateinit var imageResIds: Map<Int, Int>

    init {
        imageResIds = hashMapOf<Int, Int>(
            UtilHelper.hashCodeOf(0,0) to R.drawable.row_1_column_1,
            UtilHelper.hashCodeOf(0,1) to R.drawable.row_1_column_2,
            UtilHelper.hashCodeOf(0,2) to R.drawable.row_1_column_3,
            UtilHelper.hashCodeOf(0,3) to R.drawable.row_1_column_4,
            UtilHelper.hashCodeOf(0,4) to R.drawable.row_1_column_5,
            UtilHelper.hashCodeOf(1,0) to R.drawable.row_2_column_1,
            UtilHelper.hashCodeOf(1,1) to R.drawable.row_2_column_2,
            UtilHelper.hashCodeOf(1,2) to R.drawable.row_2_column_3,
            UtilHelper.hashCodeOf(1,3) to R.drawable.row_2_column_4,
            UtilHelper.hashCodeOf(1,4) to R.drawable.row_2_column_5,
            UtilHelper.hashCodeOf(2,0) to R.drawable.row_3_column_1,
            UtilHelper.hashCodeOf(2,1) to R.drawable.row_3_column_2,
            UtilHelper.hashCodeOf(2,2) to R.drawable.row_3_column_3,
            UtilHelper.hashCodeOf(2,3) to R.drawable.row_3_column_4,
            UtilHelper.hashCodeOf(2,4) to R.drawable.row_3_column_5,
            UtilHelper.hashCodeOf(3,0) to R.drawable.row_4_column_1,
            UtilHelper.hashCodeOf(3,1) to R.drawable.row_4_column_2,
            UtilHelper.hashCodeOf(3,2) to R.drawable.row_4_column_3,
            UtilHelper.hashCodeOf(3,3) to R.drawable.row_4_column_4,
            UtilHelper.hashCodeOf(3,4) to R.drawable.row_4_column_5,
            UtilHelper.hashCodeOf(4,0) to R.drawable.row_5_column_1,
            UtilHelper.hashCodeOf(4,1) to R.drawable.row_5_column_2,
            UtilHelper.hashCodeOf(4,2) to R.drawable.row_5_column_3,
            UtilHelper.hashCodeOf(4,3) to R.drawable.row_5_column_4,
            UtilHelper.hashCodeOf(4,4) to R.drawable.row_5_column_5,


//            UtilHelper.hashCodeOf(0,0) to R.drawable.heart_row_1_column_1,
//            UtilHelper.hashCodeOf(0,1) to R.drawable.heart_row_1_column_2,
//            UtilHelper.hashCodeOf(0,2) to R.drawable.heart_row_1_column_3,
//            UtilHelper.hashCodeOf(0,3) to R.drawable.heart_row_1_column_4,
//
//            UtilHelper.hashCodeOf(1,0) to R.drawable.heart_row_2_column_1,
//            UtilHelper.hashCodeOf(1,1) to R.drawable.heart_row_2_column_2,
//            UtilHelper.hashCodeOf(1,2) to R.drawable.heart_row_2_column_3,
//            UtilHelper.hashCodeOf(1,3) to R.drawable.heart_row_2_column_4,
//
//            UtilHelper.hashCodeOf(2,0) to R.drawable.heart_row_3_column_1,
//            UtilHelper.hashCodeOf(2,1) to R.drawable.heart_row_3_column_2,
//            UtilHelper.hashCodeOf(2,2) to R.drawable.heart_row_3_column_3,
//            UtilHelper.hashCodeOf(2,3) to R.drawable.heart_row_3_column_4,
//
//            UtilHelper.hashCodeOf(3,0) to R.drawable.heart_row_4_column_1,
//            UtilHelper.hashCodeOf(3,1) to R.drawable.heart_row_4_column_2,
//            UtilHelper.hashCodeOf(3,2) to R.drawable.heart_row_4_column_3,
//            UtilHelper.hashCodeOf(3,3) to R.drawable.heart_row_4_column_4,
        )

    }

    public fun getCurrentImage(): Int {
        return imageResIds[UtilHelper.hashCodeOf(currentRow, currentCol)]!!
    }
    fun animate() {
        currentCol++
        if (currentCol >= colCount) {
            currentCol = 0
            currentRow++
            if (currentRow >= rowCount) {
                finish = true
                // reset
                currentRow = 0
                currentCol = -1
            }
        }
    }

    fun isFinish(): Boolean {
        return finish
    }

    fun start() {
        finish = false
    }
}