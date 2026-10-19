package com.labs15.model

data class Contact(
    val id: Int,
    val name: String,
    val phone: String,
    val email: String,
    val isFavorite: Boolean = false
)