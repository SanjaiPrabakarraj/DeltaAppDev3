package com.example.testappplication4

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkClient {
    lateinit var retrofit: Retrofit
    val BASE_URL = "https://api.thedogapi.com"

    fun getRetrofitInstance(): Retrofit {
        var okHttpClient = OkHttpClient.Builder().build()
        if(retrofit == null){
            retrofit = Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).client(okHttpClient)
                .build()
        }
        return retrofit
    }
}