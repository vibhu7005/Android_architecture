package com.example.myapplication.data

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    
    private const val BASE_URL = "https://dummyjson.com/"
    
    private val loggingInterceptor = HttpLoggingInterceptor { message ->
        Log.d("API_LOG", message)
    }.apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()
    
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
    
    val productApiService: ProductApiService by lazy {
        retrofit.create(ProductApiService::class.java)
    }
}