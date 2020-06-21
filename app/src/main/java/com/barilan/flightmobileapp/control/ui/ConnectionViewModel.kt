package com.barilan.flightmobileapp.control.ui

import android.app.Application
import android.graphics.BitmapFactory
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import com.barilan.flightmobileapp.control.connection.ConnectionRepository
import com.barilan.flightmobileapp.control.connection.RetrofitBuilder
import com.barilan.flightmobileapp.control.connection.WebService
import com.barilan.flightmobileapp.control.data.Command
import com.barilan.flightmobileapp.control.data.Result
import kotlinx.android.synthetic.main.activity_connection.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET
import java.lang.Exception

class ConnectionViewModel (val activity: AppCompatActivity): ViewModel(){
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

        //try with coroutines
        viewModelScope.launch(Dispatchers.IO) {
            var deferredResult = api?.setCommand(command)
            try{
                var result:Result? = deferredResult?.await()
                if (result != null){
                    withContext(Dispatchers.Main){
                        Log.i("@ZIV", "onResponse ${result.message} ${result.resultType}")
                    }

                }
            }catch(e:Exception){
                withContext(Dispatchers.Main){
                    Log.i("@ZIV", "onError ${e}")
                }

            }
        }

        /*api?.setCommand(command)?.enqueue(object : Callback<Result> {
            override fun onResponse(
                call: Call<Result>,
                response: Response<Result>
            ) {
                var result:Result? = response.body()
                Log.i("@ZIV", "onResponse ${result?.message} ${result?.resultType}")

                val isOk = response.code()==200
                if(!isOk){
                    if(result?.resultType == "InvalidCommand"){
                        activity.runOnUiThread{
                            Toast.makeText(activity.applicationContext,result?.message,Toast.LENGTH_SHORT).show()
                            Log.i("@ZIV", "$result?.message  in VM")
                        }
                    }else if(result?.resultType == "ServerError"){
                        activity.runOnUiThread{
                            Toast.makeText(activity.applicationContext,result?.message,Toast.LENGTH_SHORT).show()
                            Log.i("@ZIV", result?.message + "in VM")
                        }
                    }
                }
            }
            override fun onFailure(call: Call<Result>, t: Throwable) {
                activity.runOnUiThread{
                    Log.i("@ZIV", "on Failure ${call}")
                    Toast.makeText(activity.applicationContext,t.toString(),Toast.LENGTH_SHORT).show()
                    Log.i("@ZIV", t.toString() + "Failure")
                }

            }
        })*/
    }
}