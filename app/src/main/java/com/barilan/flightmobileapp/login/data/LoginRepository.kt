package com.barilan.flightmobileapp.login.data

import androidx.lifecycle.LiveData

class LoginRepository(private val urlDAO: URLAddressDAO) {
    val allAddresses: LiveData<List<String>> = urlDAO.getAddresses()

    suspend fun insertAddress(address: String) {
        val urlEntity = URLAddressEntity(address)
        urlDAO.insert(urlEntity)
    }
    suspend fun updateTime(address: String) {
        val updatedAddress = URLAddressEntity(address)
        urlDAO.update(updatedAddress)
    }

}