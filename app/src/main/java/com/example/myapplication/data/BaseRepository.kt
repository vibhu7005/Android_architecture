package com.example.myapplication.data

import retrofit2.HttpException
import kotlinx.coroutines.CancellationException
import javax.inject.Inject

abstract class BaseRepository() {

    @Inject
    lateinit var globalEventManager: GlobalEventManager
    protected suspend fun <T> executeApiCall(apiCall: suspend () -> T): Result<T> {
        return try {
            Result.success(apiCall())
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