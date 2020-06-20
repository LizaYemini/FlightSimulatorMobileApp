package com.barilan.flightmobileapp.control.ui

import android.app.Application
import android.graphics.BitmapFactory
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.barilan.flightmobileapp.control.connection.ConnectionRepository
import com.barilan.flightmobileapp.control.connection.RetrofitBuilder
import com.barilan.flightmobileapp.control.connection.WebService
import com.barilan.flightmobileapp.control.data.Command
import kotlinx.android.synthetic.main.activity_connection.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import okhttp3.Dispatcher

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET

class ConnectionViewModel (url: String): ViewModel(){
    private var api: WebService? = RetrofitBuilder.getInstance()
    private var rudder:Float = 0.0f
    private var elevator:Float = 0.0f
    private var aileron:Float = 0.0f
    private var throttle:Float = 0.0f

    fun setCommand(name:String,value:Float){
        when(name){
            "rudder" -> rudder = value
            "elevator" ->elevator = value
            "aileron"-> aileron = value
            "throttle" ->throttle = value
        }
        val command: Command = Command(rudder,elevator,aileron,throttle)
        api?.setCommand(command)?.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                val isOk = response.code()==200
                if(isOk){

                }
            }
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

            }
        })
    }
}