package com.example.myapplication.data

import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject
import javax.inject.Singleton



sealed class GlobalEvent {
    data class Logout(val message: String) : GlobalEvent()
}

@Singleton
class GlobalEventManager @Inject constructor() {
    private val _events = Channel<GlobalEvent>()
    val events = _events.receiveAsFlow()

    fun sendEvent(event: GlobalEvent) {
        _events.trySend(event)
    }
}