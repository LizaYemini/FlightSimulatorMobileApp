package com.barilan.flightmobileapp.login.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.barilan.flightmobileapp.R
import com.barilan.flightmobileapp.control.connection.RetrofitBuilder
import com.barilan.flightmobileapp.control.ui.ControlActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern
import kotlin.collections.ArrayList


class LoginActivity : AppCompatActivity() {
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private lateinit var vm: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        vm = ViewModelProvider(this).get(LoginViewModel::class.java)
        vm.allAddresses.observe(this, Observer
        { addresses -> addresses.let {this.renderAddresses(it)}})
        addressesList.setOnItemClickListener{ parent, _, position, _ ->
            chosenUrl.setText(parent.getItemAtPosition(position).toString())
        }
        connectButton.setOnClickListener {
            val address = chosenUrl.text.toString()
            if (isValidUrl(address)) {
                vm.insertAddress(address)
                tryToConnect(address)
            }
            else {
                Toast.makeText(this@LoginActivity, "The address: " + address
                        + " is not a valid url, please try again", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun renderAddresses(addresses: List<String>) {
        //Log.i("@AKTDEV", "<<in renderAddresses>>")
        val arrayAddresses: ArrayList<String> = ArrayList()
        for (i in addresses.indices) {
            arrayAddresses.add(addresses[i])
            //Log.i("@AKTDEV", "$arrayAddresses")
        }
        val adapter = ArrayAdapter(this,R.layout.listview_item, arrayAddresses)
        addressesList.adapter = adapter
    }

    private fun tryToConnect(address: String) {
        val self = this
        val api = RetrofitBuilder.build(address)
        uiScope.launch(Dispatchers.IO) {
            val deferredResult = api.getImg()
            try{
                val result = deferredResult.await()
                withContext(Dispatchers.Main) {
                    val i = Intent(self, ControlActivity::class.java)
                    startActivity(i)
                }
            } catch(ex:Exception){
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@LoginActivity, ex.toString(), Toast.LENGTH_SHORT).show()
                    Log.i("@LOGIN", ex.toString())
                }
            }
        }
            /*api.getImg().enqueue(object : Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                val i = Intent(self, ControlActivity::class.java)
                startActivity(i)
                    //Toast.makeText(this@LoginActivity, "Connected", Toast.LENGTH_SHORT).show()
            }
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                /*Toast.makeText(this@LoginActivity, "Couldn't connect with the address: " + address
                        + " Please Try Again", Toast.LENGTH_SHORT).show() */
                Toast.makeText(this@LoginActivity,t.toString(),Toast.LENGTH_SHORT).show()
                Log.i("@LOGIN", t.toString())
            }
        })*/
    }

    private fun isValidUrl(url: String): Boolean {
        val p: Pattern = Patterns.WEB_URL
        val m: Matcher = p.matcher(url.toLowerCase(Locale.ROOT))
        return m.matches()
    }
}
