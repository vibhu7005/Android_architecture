package com.example.myapplication.domain

import android.util.Log

class AddEventUseCase(private val repository: EventRepository) {

    suspend operator fun invoke(name: String, data : Map<String, String>) {
        //will be fetched from firebase or so
        if (repository.getEventCount() < 100) {
           repository.deleteOldestEvents()
           Log.w("EventTracker", "Event count exceeded 100, deleting oldest events")
        }
        repository.insertEvent(Event(eventName = name, eventDetails = data))
    }
}