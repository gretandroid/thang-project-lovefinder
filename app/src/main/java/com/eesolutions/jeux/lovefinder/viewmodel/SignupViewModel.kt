package com.eesolutions.jeux.lovefinder.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eesolutions.jeux.lovefinder.game.util.SettingHelper
import com.eesolutions.jeux.lovefinder.model.User
import com.eesolutions.jeux.lovefinder.webservice.RetrofitInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class SignupViewModel : ViewModel(){

    fun signup(login: String, password: String, deviceId: String, runnable: Runnable){
        var user : User?
        viewModelScope.launch {
            try {
                CoroutineScope(Dispatchers.Main).launch {
                    user = RetrofitInstance.userDao.create(User(0, deviceId, login, password, 0))
                }.join()
                runnable.run()
             }
            catch (e : Exception)
            {

            }
            finally {

            }
        }
    }
}