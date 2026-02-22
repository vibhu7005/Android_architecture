package com.example.myapplication.data

import com.example.myapplication.domain.Todo


fun TodoEntity.toDomain(): Todo {
    return Todo(
        id = this.id,
        taskDescription = this.description,
        isCompleted = this.isCompleted,
        eta = this.eta,
        createdAt = this.createdAt,
        priority = this.priority
    )
}


fun Todo.toEntity(): TodoEntity {
    return TodoEntity(
        id = this.id,
        title = this.taskDescription,
        description = this.taskDescription,
        isCompleted = this.isCompleted,
        eta = this.eta,
        createdAt = this.createdAt,
        priority = this.priority
    )
}
