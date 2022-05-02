package com.eesolutions.jeux.lovefinder.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eesolutions.jeux.lovefinder.game.model.BoyCharacter

class MainGameViewModel : ViewModel() {
    private val _boyCharacter = MutableLiveData<BoyCharacter>()

    public val boyCharacter : LiveData<BoyCharacter> = _boyCharacter

    init {

    }
}