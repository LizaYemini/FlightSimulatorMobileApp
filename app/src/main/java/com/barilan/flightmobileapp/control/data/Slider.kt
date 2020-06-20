package com.barilan.flightmobileapp.control.data

import android.widget.SeekBar
import android.widget.TextView
import com.barilan.flightmobileapp.control.ui.ConnectionViewModel

class Slider(var name:String,var view: TextView,val connectionViewModel: ConnectionViewModel): SeekBar.OnSeekBarChangeListener {
    var progressChangedValue:Float = 0.0f
    override fun onProgressChanged(
        seekBar: SeekBar,
        progress: Int,
        fromUser: Boolean
    ) {
        if(Math.abs(progress-progressChangedValue*100)>2){
            progressChangedValue = changeToDouble(progress)
        }
        view.text = progressChangedValue.toString()
        connectionViewModel.setCommand(name,progressChangedValue)
        //TODO("send to ViewModel")


    }
    fun changeToDouble(progress:Int):Float {
        return ((progress*0.01).toFloat())
    }


    override fun onStartTrackingTouch(seekBar: SeekBar) {
        // TODO Auto-generated method stub
    }

    override fun onStopTrackingTouch(seekBar: SeekBar) {
        /*Toast.makeText(
            this@MainActivity, "Seek bar progress is :$progressChangedValue",
            Toast.LENGTH_SHORT
        ).show()*/

    }
}