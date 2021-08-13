package com.example.testappplication4.Retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    var httpLoggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    var okHttpClient = OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build()

    val api: DogAPI by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.thedogapi.com")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(DogAPI::class.java)
    }

}