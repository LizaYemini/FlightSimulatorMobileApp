package com.barilan.flightmobileapp.control.ui

import androidx.activity.viewModels
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.barilan.flightmobileapp.R
import com.barilan.flightmobileapp.login.ui.LoginViewModel

class ConnectionActivity : AppCompatActivity() {
    //val viewModel: ConnectionViewModel by viewModels()
    //lateinit var connectionVM: ConnectionViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connection)

        /* change to
        Bundle b = getIntent().getExtras();
        connectionVM = b.getInt("connectionVM")
         */
        //connectionVM = ViewModelProvider(this).get(ConnectionViewModel::class.java)
        //viewModel.getImg.observe(this, Observer {
    }
}
