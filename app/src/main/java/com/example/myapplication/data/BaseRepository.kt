package com.example.myapplication.data

import retrofit2.HttpException
import kotlinx.coroutines.CancellationException
import javax.inject.Inject

abstract class BaseRepository() {

    @Inject
    lateinit var globalEventManager: GlobalEventManager
    protected suspend fun <T> executeApiCall(apiCall: suspend () -> BaseResponse<T>): Result<T> {
        return try {
            val response = apiCall()
            if (response.status == "OK") {
                response.data?.let {
                    Result.success(it)
                } ?: Result.failure(Exception(response.message))
            } else {
                Result.failure(Exception(response.message))
            }
        } catch (e: CancellationException) {
            throw e
        } catch (e: HttpException) {
            if (e.code() == 401) {
                globalEventManager.sendEvent(GlobalEvent.Logout("Unauthorized access - logging out"))
            }
            Result.failure(e)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}