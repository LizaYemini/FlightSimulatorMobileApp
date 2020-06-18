package com.barilan.flightmobileapp.control.connection

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET

interface WebService {
    @GET("/screenshot")
    fun getImg(): Call<ResponseBody>
}