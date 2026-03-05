package com.example.myapplication.data

data class BaseResponse<T>(val status : String,
    val message : String?, val data : T?)