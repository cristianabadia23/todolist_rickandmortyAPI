package com.example.myapplication.constants


enum class TaskState(val displayName: String) {
    ALL("Todas"),
    IN_PROGRESS("En progreso"),
    COMPLETED("Completada"),
    DELETED("Eliminada");

    companion object {
        fun from(value: String): TaskState {
            return values().firstOrNull { it.name.equals(value, ignoreCase = true) } ?: ALL
        }
    }
}