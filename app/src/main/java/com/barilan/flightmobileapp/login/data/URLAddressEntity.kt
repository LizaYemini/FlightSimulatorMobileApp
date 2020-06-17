package com.barilan.flightmobileapp.login.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "url_address")
data class URLAddressEntity
(
    @PrimaryKey
    val address: String,
    @ColumnInfo (name = "last_login_date")
    var lastLoginDate: Long = System.currentTimeMillis()
)