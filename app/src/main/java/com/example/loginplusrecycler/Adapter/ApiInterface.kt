package com.example.loginplusrecycler.Adapter

import com.example.loginplusrecycler.UsersItem
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {

    @GET("/users")
    fun getData() : Call<List<UsersItem>>

}