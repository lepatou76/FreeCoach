package com.example.freecoach


data class Player(
    val id: Int,
    val lastName: String,
    val firstName: String,
    val strongMax: Int = 1,
    val strongImage: Int = R.drawable.bar_of_six,
    val weakMax: Int = 1,
    val weakImage: Int = R.drawable.bar_of_six,
    val headMax: Int = 1,
    val headImage: Int = R.drawable.bar_of_six,
    val totalMax: Int = 3,
    val totalImage: Int = R.drawable.bar_of_six,
    val nbMatchs: Int = 0,
    val playtime: Int = 0,
    val scored: Int = 0
)
