package com.example.solve_it.models;

data class User(
    val email: String,
    val login: String,
    val password: String,
    val history: List<String> = listOf("registered")
)
