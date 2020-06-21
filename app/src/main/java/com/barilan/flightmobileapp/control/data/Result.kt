package com.barilan.flightmobileapp.control.data

import com.google.gson.annotations.SerializedName

class Result {
    @SerializedName("result_type")
    var resultType:String? = null
    @SerializedName("message")
    var message:String? = null
}