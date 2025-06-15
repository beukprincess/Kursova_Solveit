package com.example.solve_it.models;

data class User(
    val id: String? = null,
    val email: String,
    val login: String,
    val password: String,
    val history: List<String> = listOf("registered")
)
