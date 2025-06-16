package com.example.solve_it.models;

data class QueryHistory(
    val queryHistoryId: String? = null,
    val userId: String,
    val taskText: String? = null,
    val solution: String? = null,
    val answer: String? = null
)