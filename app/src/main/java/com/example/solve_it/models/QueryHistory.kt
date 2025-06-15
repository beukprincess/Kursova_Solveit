package com.example.solve_it.models

data class QueryHistory(
    val queryHistoryId: String? = null,
    val userId: String?,
    val taskText: String,
    val solution: List<String>
)