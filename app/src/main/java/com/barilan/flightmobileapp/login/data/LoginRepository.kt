package com.barilan.flightmobileapp.login.data

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext


class LoginRepository(private val urlDAO: URLAddressDAO) {
    val allAddresses: LiveData<List<String>> = urlDAO.getAddresses()

    suspend fun insertAddress(address: String) {
        Log.i("@AKTDEV", "<<start LoginRepository(insertAddress)>>")
        Log.i("@AKTDEV", "inside LoginRepository: $address")
        val urlEntity = URLAddressEntity(address)
        Log.i("@AKTDEV", "url entity: $urlEntity")
        urlDAO.insert(urlEntity)
        Log.i("@AKTDEV", "<<end LoginRepository(insertAddress)>>")
    }

    //suspend fun getAddresses() = urlDAO.getAddresses()
    /*
    fun getAddresses(): List<String> {
        urlDAO.getAddresses().forEach{
            Log.i("@AKTDEV", """url is : $it""")
        }
        return urlDAO.getAddresses()
    } */

    suspend fun updateTime(address: String) {
        val updatedAddress = URLAddressEntity(address)
        urlDAO.update(updatedAddress)
    }

}


/*
/*
class LoginRepository(application: Application): CoroutineScope {
    private var urlDAO: URLAddressDAO?

    init {
        val db = LoginDB.getInstance(application)
        urlDAO = db?.addressDAO()
    }

    suspend fun insertAddress(address: String) {
        var urlEntity = URLAddressEntity(address)
        urlDAO?.insert(urlEntity)
    }

    suspend fun getAddresses() = urlDAO?.getAddresses()

    suspend fun updateTime(address: String) {
        val updatedAddress = URLAddressEntity(address)
        urlDAO?.update(updatedAddress)
    }
} */