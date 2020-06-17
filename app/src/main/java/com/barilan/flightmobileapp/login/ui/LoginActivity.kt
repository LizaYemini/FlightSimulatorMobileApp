package com.barilan.flightmobileapp.login.ui

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.barilan.flightmobileapp.R
import com.barilan.flightmobileapp.login.data.GetApi
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class LoginActivity : AppCompatActivity() {

    private lateinit var vm: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        vm = ViewModelProvider(this).get(LoginViewModel::class.java)
        vm.allAddresses.observe(this, Observer
        { addresses -> addresses.let {this.renderAddresses(it)}})
        addressesList.setOnItemClickListener{ parent, _, position, _ ->
            chosenUrl.setText(parent.getItemAtPosition(position).toString())
        }
        connectButton.setOnClickListener {
            val address = chosenUrl.text.toString()
            vm.insertAddress(address)
            tryToConnect(address)
        }
    }
    private fun renderAddresses(addresses: List<String>) {
        Log.i("@AKTDEV", "<<in renderAddresses>>")
        val arrayAddresses: ArrayList<String> = ArrayList()
        for (i in addresses.indices) {
            arrayAddresses.add(addresses[i])
            Log.i("@AKTDEV", "$arrayAddresses")
        }
        val adapter = ArrayAdapter(this,R.layout.listview_item, arrayAddresses)
        addressesList.adapter = adapter
    }

    private fun tryToConnect(address: String) {
        val gson = GsonBuilder().setLenient().create()
        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:/5200/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        val api = retrofit.create(GetApi::class.java)
        val body = api.getImg().enqueue(object : Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                    Toast.makeText(this@LoginActivity, "Connected", Toast.LENGTH_SHORT).show()
            }
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(this@LoginActivity, "Couldn't connect with the address: " + address
                        + " Please Try Again", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
