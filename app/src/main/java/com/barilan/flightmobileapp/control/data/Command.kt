package com.barilan.flightmobileapp.control.data

import com.google.gson.annotations.SerializedName

data class Command(
    @SerializedName("rudder") val rudder: Float?,
    @SerializedName("elevator") val elevator: Float?,
    @SerializedName("aileron") val aileron: Float?,
    @SerializedName("throttle") val throttle: Float?
    )