package com.example.myapplication

import kotlinx.coroutines.CancellationException
import okio.IOException

abstract class BaseRepository {

    protected suspend fun <T> safeApiCall(apiCall: suspend () -> T): Result<T> {
        return try {
            val response = apiCall()
            Result.success(response)
        } catch (ex: CancellationException) {
            throw ex
        } catch (ex: IOException) {
            Result.failure(Exception("Network error"))
        } catch (ex: Exception) {
            Result.failure(Exception("Something went wrong"))
        }
    }
}