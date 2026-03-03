package com.example.myapplication.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {


    }
}


suspend fun foo(): Int {



//    val scope = CoroutineScope(Dispatchers.IO)
//
//    scope.launch {
//        val a = async {
//            repeat(10) {
//                println(it)
//                delay(1000L)
//            }
//        }
//
//        val b = async {
//
//        }
//    }
    delay(2300)
    return 5
}