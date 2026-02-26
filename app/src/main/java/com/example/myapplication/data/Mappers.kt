package com.example.myapplication.data

import com.example.myapplication.domain.Notes

fun NotesEntity.toDomain() : Notes {
    return Notes(
        id = id,
        title = title,
        description = description,
        time = time
    )
}

