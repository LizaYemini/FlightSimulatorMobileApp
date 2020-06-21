package com.barilan.flightmobileapp.control.connection

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitBuilder {
    companion object {
        private var INSTANCE: WebService? = null
        private var URL: String? = null
        fun build(url: String):  WebService {
            val client:OkHttpClient  = OkHttpClient.Builder()
                .connectTimeout(10,TimeUnit.SECONDS)
                .readTimeout(10,TimeUnit.SECONDS)
                .writeTimeout(10,TimeUnit.SECONDS)
                .build()

            val tempInstance = INSTANCE
            if (tempInstance != null && url == URL) {
                return tempInstance
            }
            val instance = Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .client(client)
                .build().create(WebService::class.java)
            INSTANCE = instance
            return instance
        }
        fun getInstance(): WebService? {
            return INSTANCE
        }
    }
}
