package com.example.myapplication.data.repo

import kotlinx.coroutines.CancellationException
import retrofit2.HttpException

interface SafeApiCall {
    suspend  fun <T> safeApiCall(apiCall: suspend () -> T): Result<T> {
        return try {
            Result.success(apiCall.invoke())
        } catch (ex: CancellationException) {
            throw ex
        } catch (ex: HttpException) {
            Result.failure(ex)
        } catch (ex: Exception) {
            Result.failure(ex)
        }
    }
}