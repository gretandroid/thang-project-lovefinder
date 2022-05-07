package com.eesolutions.jeux.lovefinder.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eesolutions.jeux.lovefinder.game.model.BoyCharacter
import com.eesolutions.jeux.lovefinder.game.model.GirlCharater
import com.eesolutions.jeux.lovefinder.game.model.MatchObject
import com.eesolutions.jeux.lovefinder.model.GUEST_USER
import com.eesolutions.jeux.lovefinder.model.User
import com.eesolutions.jeux.lovefinder.webservice.RetrofitInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Exception

class MainGameViewModel : ViewModel() {
    private val _boyCharacter = MutableLiveData<BoyCharacter>()
    private val _girlCharaterList = MutableLiveData<MutableList<GirlCharater>>()
    private val _matchObject = MutableLiveData<MatchObject>()

    val boyCharacter : LiveData<BoyCharacter> = _boyCharacter
    val girlCharaterList : LiveData<MutableList<GirlCharater>> = _girlCharaterList
    val matchObject : LiveData<MatchObject> = _matchObject

    private val _user = MutableLiveData<User>()
    val user : LiveData<User> = _user

    fun onMessageReveived(user: User) {
        _user.value = user
    }

    var running = false

    fun start() {
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

                // check match
                if (_matchObject.value!!.isFinish()) {
                    val matchedGirls = _boyCharacter.value!!.findMatch(_girlCharaterList.value!!)
                    if (!matchedGirls!!.isEmpty()) {
                        // start match
                        _matchObject.value!!.start()
                        _matchObject.value?.x = _boyCharacter.value!!.x
                        _matchObject.value?.y = _boyCharacter.value!!.y
                        // increase score
                        _boyCharacter.value?.increaseScore()
                        _user.value!!.score = _boyCharacter.value!!.score
                        _user.value = _user.value // for notification

                        // persist score
                        if (_user.value !== GUEST_USER) {
                            saveUser(_user.value!!)
                        }

                        // spawn new girl at the center of screen
                        // check if not enough girl => spawn
                        matchedGirls.forEach {
                            it.goHome()
//                            it.x = it.surfaceWidth/2
//                            it.y = it.surfaceHeight/2
//                            it.movingVectorX = (-15..10).random()
//                            it.movingVectorY = (-12..20).random()
                        }

                        _girlCharaterList.value = _girlCharaterList.value
                    }
                } else {
                    _matchObject.value!!.animate()
                    _matchObject.value = _matchObject.value
                }


                // wait an interval to refresh game screen
                waitTime = (System.nanoTime() - startTime)/1000000
                waitTime = if (waitTime < 100)  100 else waitTime

                delay(waitTime)
//                Log.d("App", "[x,y] = [${_boyCharacter.value?.x},${_boyCharacter.value?.y}]")
                startTime = System.nanoTime()

            }
        }
    }

    private fun saveUser(user : User) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                RetrofitInstance.userDao.update(user.id, user)
            } catch (e: Exception) {
                Log.d(
                    "App",
                    "Error while saving score of user ${user.login} : ${e.message}"
                )
            }
        }
    }

    fun initBoyCharacter(charater : BoyCharacter) {
        _boyCharacter.value = charater
    }

    fun initGrilCharacters(girlCharaters: List<GirlCharater>) {
        _girlCharaterList.value = girlCharaters.toMutableList()
    }

    fun changeDirectionOnCharacter(x: Int, y: Int) : Boolean {
        Log.d("App", "Event Click [x,y] = [${x},${y}]")

        val movingVectorX = x - _boyCharacter.value!!.x
        val movingVectorY = y - _boyCharacter.value!!.y
        _boyCharacter.value!!.setMovingVector(movingVectorX, movingVectorY)
        return true
    }

    fun initMatchObject(matchObject: MatchObject) {
        _matchObject.value = matchObject
    }

    fun initScore(newGame: Boolean) {
        when(newGame) {
            true ->
            {
                _user.value!!.score = 0
            }
            else -> {

            }
        }
        _boyCharacter.value?.score = _user.value!!.score

        // notify UI
        _boyCharacter.value =  _boyCharacter.value
        _user.value = _user.value
    }


}