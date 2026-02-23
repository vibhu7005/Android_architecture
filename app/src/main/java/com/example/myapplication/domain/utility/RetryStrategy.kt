package com.example.myapplication.domain.utility

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.annotations.Async
import kotlin.math.pow

suspend fun <T>retryWithExponentialBackOff(retries : Int = 3, maxDelay : Long = 10000L, initialDelay : Long = 1000L, block : suspend() -> T) : T {
    repeat(retries) { attempt ->
        try {
            return block()
        } catch (e: Exception) {
            if (attempt == retries - 1) {
                throw e
            }
            val delayTime = (initialDelay * 2.0.pow(attempt.toDouble())).toLong().coerceAtMost(maxDelay)
            delay(delayTime)
        }
    }
    throw Exception("Failed after $retries attempts")
}