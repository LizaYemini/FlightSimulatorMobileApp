package com.barilan.flightmobileapp.control.connection

import android.util.Log
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitBuilder {

    companion object {
        private var INSTANCE: WebService? = null
        private var URL: String? = null
        fun build(url: String):  WebService {
            val tempInstance = INSTANCE
            if (tempInstance != null && url == URL) {
                return tempInstance
            }
            val instance = Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .build().create(WebService::class.java)
            INSTANCE = instance
            return instance
        }
        fun getInstance(): WebService? {
            return INSTANCE
        }
    }
}
