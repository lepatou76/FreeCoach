package com.example.freecoach

data class Player(
    val id: Int,
    val lastName: String,
    val firstName: String,
    val strongMax: Int = 1,
    val weakMax: Int = 1,
    val headMax: Int = 1,
    val totalMax: Int = 3,
    val playtime: Int = 0,
    val scored: Int = 0
)
