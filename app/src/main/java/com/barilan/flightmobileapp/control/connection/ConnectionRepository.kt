package com.barilan.flightmobileapp.control.connection

import com.barilan.flightmobileapp.control.data.Command

class ConnectionRepository (url: String){
    var client: WebService = RetrofitBuilder.build(url)

    suspend fun getImg() = client.getImg()
    suspend fun setCommand(command: Command) = client.setCommand(command)
}