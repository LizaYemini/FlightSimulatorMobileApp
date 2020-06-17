package com.barilan.flightmobileapp.login.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface URLAddressDAO
{
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(address: URLAddressEntity)

    @Query("SELECT * FROM url_address")
    fun getAll(): List<URLAddressEntity>

    @Query("SELECT address FROM url_address ORDER BY last_login_date DESC LIMIT 5")
    fun getAddresses(): LiveData<List<String>>

    @Delete
    suspend fun delete(address: URLAddressEntity)

    @Update
    suspend fun update(address: URLAddressEntity)

    suspend fun updateDate(address: URLAddressEntity) {
        address.lastLoginDate = System.currentTimeMillis()
        update(address)
    }
}