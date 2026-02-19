package com.example.myapplication.presenter

sealed class ProductEvent {
    data class Error(val message : String) : ProductEvent()
}