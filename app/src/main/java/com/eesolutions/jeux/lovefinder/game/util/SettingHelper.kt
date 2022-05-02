package com.eesolutions.jeux.lovefinder.game.util

import android.content.Context
import android.provider.Settings

object SettingHelper {

    fun getDeviceId(context : Context) : String {
        return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    }
}