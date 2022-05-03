package com.eesolutions.jeux.lovefinder.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eesolutions.jeux.lovefinder.game.model.BoyCharacter
import com.eesolutions.jeux.lovefinder.game.model.GirlCharater
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainGameViewModel : ViewModel() {
    private val _boyCharacter = MutableLiveData<BoyCharacter>()
    private val _girlCharaterList = MutableLiveData<MutableList<GirlCharater>>()

    public val boyCharacter : LiveData<BoyCharacter> = _boyCharacter
    public val girlCharaterList : LiveData<MutableList<GirlCharater>> = _girlCharaterList

    public var running = false

    public fun start() {
        viewModelScope.launch {
            var startTime = System.nanoTime()
            var waitTime : Long
            while (running
            ) {
                // update state of game objects here
                    // boy
                _boyCharacter.value?.moveOneStep()
                _boyCharacter.value = _boyCharacter.value

                // girls
                _girlCharaterList.value?.forEach {
                    it.moveOneStep()
                }
                _girlCharaterList.value = _girlCharaterList.value


                // wait an interval to refresh game screen
                waitTime = (System.nanoTime() - startTime)/1000000
                waitTime = if (waitTime < 10)  10 else waitTime

                delay(100)
                Log.d("App", "[x,y] = [${_boyCharacter.value?.x},${_boyCharacter.value?.y}]")
                startTime = System.nanoTime()

            }
        }
    }

    public fun initBoyCharacter(charater : BoyCharacter) {
        _boyCharacter.value = charater
    }

    public fun initGrilCharacters(girlCharaters: List<GirlCharater>) {
        _girlCharaterList.value = girlCharaters.toMutableList()
    }

    fun changeDirectionOnCharacter(x: Int, y: Int) : Boolean {
        Log.d("App", "Event Click [x,y] = [${x},${y}]")

        val movingVectorX = x - _boyCharacter.value!!.x
        val movingVectorY = y - _boyCharacter.value!!.y
        _boyCharacter.value!!.setMovingVector(movingVectorX, movingVectorY)
        return true
    }


}