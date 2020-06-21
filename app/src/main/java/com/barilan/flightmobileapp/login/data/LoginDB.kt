package com.barilan.flightmobileapp.login.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [URLAddressEntity::class], version = 1, exportSchema = false)
abstract class LoginDB : RoomDatabase()
{
    abstract fun addressDAO(): URLAddressDAO
    companion object {
        @Volatile
        private  var INSTANCE: LoginDB? = null
        fun getInstance(context: Context): LoginDB {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = newLoginDB(context)
                INSTANCE = instance
                return instance
            }
        }
    }
}

fun newLoginDB(context: Context): LoginDB {
    return Room.databaseBuilder(
        context.applicationContext,
        LoginDB::class.java,
        "login_db")
        .fallbackToDestructiveMigration().build()
}
