package com.example.myapplication.domain

import kotlinx.coroutines.flow.Flow

interface EventRepository {

    suspend fun fetchEventBatch(batchSize: Int) : List<Event>

    suspend fun getEventCount() : Int

    suspend fun deleteOldestEvents()

    suspend fun insertEvent(event: Event)

    suspend fun incrementRetryCount(eventId : String)

    suspend fun markFailed(eventId: String)

    suspend fun deleteEvent(ids: List<String>)

    suspend fun enforceStorageLimit()

    suspend fun fetchQueuedEvents() : List<Event>

    suspend fun updateEventStatus(eventId: String, status: EventStatus)

    fun observeAllEvents() : Flow<List<Event>>
}