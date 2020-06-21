package com.barilan.flightmobileapp.control.ui

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.barilan.flightmobileapp.R
import com.barilan.flightmobileapp.control.connection.RetrofitBuilder
import com.barilan.flightmobileapp.control.connection.WebService
import com.barilan.flightmobileapp.control.data.Slider
import kotlinx.android.synthetic.main.activity_connection.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlin.math.cos
import kotlin.math.sin


class ControlActivity : AppCompatActivity() {
    private var api: WebService? = null
    private var showImg: Boolean = true
    private var stopConnection: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connection)

        api = RetrofitBuilder.getInstance()
        if (api == null) {
            askForBackToLogin(
                "There was a problem with the server"
            )
        }
        val connectionViewModel = ConnectionViewModel(this)
        rudderSlider.setOnSeekBarChangeListener(
            Slider("rudder", rudderView, connectionViewModel)
        )
        throttleSlider.setOnSeekBarChangeListener(
            Slider(
                "throttle",
                throttleView,
                connectionViewModel
            )
        )
        joystick.setOnMoveListener ({ angle, strength ->
            val newStrength = strength*0.01
            val newAngle = Math.toRadians(angle.toDouble())
            val aileron = newStrength* cos(newAngle)
            val elevator = newStrength* sin(newAngle)
            connectionViewModel.setCommand(aileron.toFloat(),elevator.toFloat())
        },500)
    }
    private fun loopImg() {
        if (showImg) {
            CoroutineScope(IO).launch {
                delay(2000)
                showImg()
                loopImg()
            }
        }
    }

    private suspend fun showImg() {
        val result = api?.getImgAsync()
        try{
            val input = result?.await()?.byteStream()
            val bit = BitmapFactory.decodeStream(input)
            withContext(Dispatchers.Main) {
                imageSimulator.setImageBitmap(bit)
            }
         }catch(ex:Exception){
            withContext(Dispatchers.Main) {
                askForBackToLogin(
                    "Couldn't get the screen shot from the simulator ")
            }
        }
    }

    fun askForBackToLogin(msg: String) {
        if (!stopConnection) {
            stopConnection = true
            val builder = AlertDialog.Builder(this)
            builder.setTitle("ALERT")
            builder.setMessage("$msg \n do you want to get back to the login page?")
            builder.setPositiveButton(android.R.string.yes) { _, _ ->
                // return to the login page
                stopConnection = false
                finish()
            }
            builder.setNegativeButton(android.R.string.no) { _, _ ->
                stopConnection = false
            }
            builder.show()
        }
    }
    override fun onStart() {
        super.onStart()
        showImg = true
        loopImg()
    }

    override fun onPause() {
        super.onPause()
        showImg = false
    }
}