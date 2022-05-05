package com.eesolutions.jeux.lovefinder.viewmodel

import android.app.Notification
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eesolutions.jeux.lovefinder.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class SigninViewModel : ViewModel() {
    private val _user = MutableLiveData<User>()
    val user : LiveData<User> = _user

    private val _error = MutableLiveData<String>();
    val error: LiveData<String> = _error;
    private val _errorVisibility = MutableLiveData<Boolean>(false);
    val errorVisibility: LiveData<Boolean> = _errorVisibility;

    fun onMessageReveived(user: User) {
        _user.value = user
    }

    fun checkPassword(password: String) : Boolean {
        val isMatched = _user.value!!.password.equals(password)
        // update live data
        viewModelScope.launch {
            if (isMatched) {
                _error.value = ""
                _errorVisibility.value = false
            }
            else {
                _error.value = "Error password"
                _errorVisibility.value = true
            }
        }
        return isMatched;
    }
}