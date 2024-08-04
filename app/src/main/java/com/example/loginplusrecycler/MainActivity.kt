package com.example.loginplusrecycler

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.loginplusrecycler.Adapter.ApiInterface
import com.example.loginplusrecycler.Adapter.MyAdapter
import com.example.loginplusrecycler.databinding.ActivityMainBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    lateinit var rvMain : RecyclerView
    lateinit var myAdapter: MyAdapter
    private var BASE_URL = "https://api.github.com"

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        binding=ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        rvMain = findViewById(R.id.recycler_view)

        rvMain.layoutManager = LinearLayoutManager(this)

        getAllData()

        auth=Firebase.auth
        binding.btnSignOut.setOnClickListener {
            auth.signOut()
            val intent=Intent(this,LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun getAllData() {

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)

        val retroData = retrofit.getData()

        retroData.enqueue(object : Callback<List<UsersItem>> {
            override fun onResponse(
                call: Call<List<UsersItem>>,
                response: Response<List<UsersItem>>
            ) {
                var data = response.body()!!

                myAdapter = MyAdapter(baseContext,data)

                rvMain.adapter = myAdapter

                Log.d("data",data.toString())
            }

            override fun onFailure(call: Call<List<UsersItem>>, t: Throwable) {

            }

        })

    }
}