package com.eesolutions.jeux.lovefinder.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

const val GUEST_ID = -1

val GUEST_USER = User(-1, "", "guest", "", 0)

@Parcelize
data class User(
    var id : Int,
    val deviceId: String,
    val login: String,
    var password: String,
    var score: Int) : Parcelable
