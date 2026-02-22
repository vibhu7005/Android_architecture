package com.example.myapplication.domain

import java.util.UUID

data class Event(
    val eventId : String = UUID.randomUUID().toString(),
    val eventName : String,
    val eventDetails : Map<String, String>,
    val timestamp : Long = System.currentTimeMillis(),
    val status : EventStatus = EventStatus.QUEUED,
    val retryCount : Int = 0
)


enum class EventStatus {
    SENT,
    FAILED,
    QUEUED
}