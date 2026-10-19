package com.labs15.model

data class Task(
    val id: Int,
    val title: String,
    val isDone: Boolean = false
)