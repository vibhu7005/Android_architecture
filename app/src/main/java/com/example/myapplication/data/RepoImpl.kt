package com.example.myapplication.data

import com.example.myapplication.domain.Notes
import com.example.myapplication.domain.NotesRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RepoImpl @Inject constructor(val dao: NotesDao) : NotesRepo {
    override suspend fun addNotes(notes: Notes): Result<Unit> =
        runCatching {
            dao.insertTodo(
                NotesEntity(
                    title = notes.title,
                    description = notes.description,
                    time = notes.time
                )
            )
        }

    override suspend fun deleteNotes(notes: Notes): Result<Unit> =
        runCatching {
            dao.deleteTodo(
                NotesEntity(
                    id = notes.id,
                    title = notes.title,
                    description = notes.description,
                    time = notes.time
                )
            )
    }

    override suspend fun getNotesCount(): Result<Int> =
        runCatching {
            dao.getNotesCount()
        }

    override fun observeNotes(): Flow<List<Notes>> {
        return dao.getAllTodos().map { entities ->
            entities.map { it.toDomain()}
        }
    }
}