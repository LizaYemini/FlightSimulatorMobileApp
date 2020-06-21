package com.barilan.flightmobileapp.control.ui

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import com.barilan.flightmobileapp.control.connection.RetrofitBuilder
import com.barilan.flightmobileapp.control.connection.WebService
import com.barilan.flightmobileapp.control.data.Command
import com.barilan.flightmobileapp.control.data.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

import java.lang.Exception
import kotlin.math.abs

class ConnectionViewModel (val activity: ControlActivity): ViewModel(){
    private var api: WebService? = RetrofitBuilder.getInstance()
    private var rudder:Float = 0.0f
    private var elevator:Float = 0.0f
    private var aileron:Float = 0.0f
    private var throttle:Float = 0.0f

    fun setCommand(name:String,value:Float) {
        when (name) {
            "rudder" -> if(abs(rudder-value) >=0.01) {
                rudder = value
                val command = Command(rudder, elevator, aileron, throttle)
                sendCommand(command)
            }
            "throttle" -> if(abs(throttle-value) >=0.01){
                throttle = value
                val command = Command(rudder, elevator, aileron, throttle)
                sendCommand(command)
            }
        }
    }
    fun setCommand(newAileron:Float,newElevator:Float){
        if(abs(aileron-newAileron) < 0.01){
            return
        }
        if(abs(elevator-newElevator) < 0.01){
            return
        }
        aileron= newAileron
        elevator = newElevator
        val command = Command(rudder, elevator, aileron, throttle)
        sendCommand(command)
    }
    //try with coroutines
    private fun sendCommand(command:Command){
        viewModelScope.launch(Dispatchers.IO) {
            val deferredResult = api?.setCommandAsync(command)
            try{
                val result:Result? = deferredResult?.await()
                checkResultType(result)
            }catch(e:Exception){
                exceptionCommand(e)
            }
        }
    }

    private suspend fun exceptionCommand(e: Exception) {
        withContext(Dispatchers.Main) {
            activity.askForBackToLogin(
                "There was a problem with the server, $e"
            )
        }
    }

    private suspend fun checkResultType(result: Result?) {
        withContext(Dispatchers.Main) {
            Log.i("checkResultType", "result type: " + result?.resultType)
            when (result?.resultType) {
                "InvalidCommand" ->
                    Toast.makeText(activity, result.message, Toast.LENGTH_SHORT).show()
                "ServerError" ->
                    result.message?.let {
                        activity.askForBackToLogin(it)
                    }
                "Ok" -> Log.i("checkResultType", "OK")
                else -> Log.i("checkResultType", "ELSE")
            }
        }
    }
}