package com.barilan.flightmobileapp.login.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.barilan.flightmobileapp.login.data.LoginDB
import com.barilan.flightmobileapp.login.data.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(application: Application)
    : AndroidViewModel(application) {
    private val repository:LoginRepository
    val allAddresses: LiveData<List<String>>
    init {
        val addressesDAO = LoginDB.getInstance(application).addressDAO()
        repository = LoginRepository(addressesDAO)
        allAddresses = repository.allAddresses
    }

    fun insertAddress(address: String) =
        viewModelScope.launch (Dispatchers.IO) {
        repository.insertAddress(address)
    }
}