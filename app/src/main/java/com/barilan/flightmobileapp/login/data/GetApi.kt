package com.barilan.flightmobileapp.login.data

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET

interface GetApi {
    @GET("/screenshot")
    fun getImg(): Call<ResponseBody>
}