package com.barilan.flightmobileapp.control.data

import android.widget.SeekBar
import android.widget.TextView
import com.barilan.flightmobileapp.control.ui.ConnectionViewModel
import kotlin.math.abs

class Slider(
    var name:String, var view: TextView, private val connectionViewModel: ConnectionViewModel
):
    SeekBar.OnSeekBarChangeListener {
    var progressChangedValue:Float = 0.0f
    override fun onProgressChanged(
        seekBar: SeekBar,
        progress: Int,
        fromUser: Boolean
    ) {
        if(abs(progress-progressChangedValue*100) >=2){
            progressChangedValue = changeToDouble(progress)
        }
        view.text = progressChangedValue.toString()
        connectionViewModel.setCommand(name,progressChangedValue)
    }
    private fun changeToDouble(progress:Int):Float {
        return ((progress*0.01).toFloat())
    }
    override fun onStartTrackingTouch(seekBar: SeekBar) {
        // TODO Auto-generated method stub
    }

    override fun onStopTrackingTouch(p0: SeekBar?) {
        // TODO Auto-generated method stub
    }
}