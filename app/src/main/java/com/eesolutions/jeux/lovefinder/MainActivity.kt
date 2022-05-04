package com.eesolutions.jeux.lovefinder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import com.eesolutions.jeux.lovefinder.game.util.SettingHelper
// this game is simulated the game in this tutorial
// https://o7planning.org/10521/android-2d-game-tutorial-for-beginners#a1815857
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main)
        // get ID of device
        val deviceID = SettingHelper.getDeviceId(this)
        Log.d("App", "Device ID = $deviceID");
    }
}