package com.example.myapplication

import coil3.network.HttpException
import kotlinx.coroutines.CancellationException
import okio.IOException

suspend fun <T> safeApiCall(apiCall: suspend () -> T): Result<T> {
    return try {
        val response = apiCall()
        Result.success(response)
    } catch (ex: CancellationException) {
        throw ex
    } catch (ex: HttpException) {
        //sho server message
        Result.failure(Exception("Network error"))
    } catch (ex: Exception) {
        Result.failure(Exception("Something went wrong"))
    }
}