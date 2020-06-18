package com.barilan.flightmobileapp.control.connection

class ConnectionRepository (url: String){
    var client: WebService = RetrofitBuilder.build(url)

    suspend fun getImg() = client.getImg()
}