package com.example.freecoach

data class Player(
    val id: Int,
    val lastName: String,
    val firstName: String,
    val strongFootMax: Int,
    val weakFootMax: Int,
    val headMax: Int,
    val totalJugglesMax: Int,
    val totalPlaytime: Int,
    val goalsScored: Int
)
