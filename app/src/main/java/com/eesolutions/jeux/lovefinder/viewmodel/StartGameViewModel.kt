package com.eesolutions.jeux.lovefinder.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eesolutions.jeux.lovefinder.model.User

class StartGameViewModel : ViewModel(){
    private val _user = MutableLiveData<User>()
    val user : LiveData<User> = _user

    fun onMessageReveived(user: User) {
        _user.value = user
    }
}