package com.eesolutions.jeux.lovefinder.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var id : Int,
    val deviceId: String,
    val login: String,
    var password: String,
    var score: Int) : Parcelable
