package com.eesolutions.jeux.lovefinder.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eesolutions.jeux.lovefinder.model.User
import com.eesolutions.jeux.lovefinder.webservice.RetrofitInstance
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LandPageViewModel : ViewModel() {

    // Recycler View
    private val _userList = MutableLiveData<List<User>>()
    val userList : LiveData<List<User>> = _userList

    // Fetching UIs
    private val _error = MutableLiveData<String>();
    val error: LiveData<String> = _error;
    private val _isVisible = MutableLiveData<Boolean>();
    val isVisible: LiveData<Boolean> = _isVisible;

    init {
        fetchAllUsers()
    }
    private fun fetchAllUsers() {
        viewModelScope.launch {
            _isVisible.value = true;
            try {
                delay(2000)
                Log.d("App", "Call getAll")
                val articles = RetrofitInstance.userDao.getAll();
                Log.d("App", articles.toString())
                _userList.value = articles
            } catch (e: Exception) {
                Log.d("App", e.toString());
                _error.value = e.toString()
            } finally {
//                delay(1000)
                _isVisible.value = false
            }
        }
    }
}