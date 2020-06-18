package com.barilan.flightmobileapp.control.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.barilan.flightmobileapp.control.connection.ConnectionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import okhttp3.Dispatcher

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET

class ConnectionViewModel (url: String): ViewModel(){
    private val repository: ConnectionRepository = ConnectionRepository(url)

    val getImg: LiveData<Call<ResponseBody>> =
        liveData(Dispatchers.IO) {
            val result = repository.getImg()
            emit(result)
        }
}