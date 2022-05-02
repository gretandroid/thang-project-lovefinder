package com.eesolutions.jeux.lovefinder.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eesolutions.jeux.lovefinder.game.model.BoyCharacter
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainGameViewModel : ViewModel() {
    private val _boyCharacter = MutableLiveData<BoyCharacter>()

    public val boyCharacter : LiveData<BoyCharacter> = _boyCharacter

    public var running = false

    public fun start() {
        viewModelScope.launch {
            var startTime = System.nanoTime()
            var waitTime : Long
            var counter : Int = 0
            while (running && counter < 1000) {
                counter++
                // update state of game objects here
                _boyCharacter.value?.moveOneStep()


                _boyCharacter.value = _boyCharacter.value

                // wait an interval to refresh game screen
                waitTime = (System.nanoTime() - startTime)/1000000
                waitTime = if (waitTime < 10)  10 else waitTime

                delay(100)
                Log.d("App", "[x,y] = [${_boyCharacter.value?.x},${_boyCharacter.value?.y}]")
                startTime = System.nanoTime()

            }
        }
    }

    public fun setBoyCharacter(charater : BoyCharacter) {
        _boyCharacter.value = charater
    }

    public fun inverse () {
        _boyCharacter.value?.inverseX()
        _boyCharacter.value = _boyCharacter.value
    }


}