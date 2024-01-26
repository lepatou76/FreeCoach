package com.example.freecoach

import android.graphics.drawable.Drawable


data class Player(
    val id: Int,
    val lastName: String,
    val firstName: String,
    val strongMax: Int = 1,
    val strongImage: Int = R.drawable.equal_than_last,
    val weakMax: Int = 1,
    val weakImage: Int = R.drawable.equal_than_last,
    val headMax: Int = 1,
    val headImage: Int = R.drawable.equal_than_last,
    val totalMax: Int = 3,
    val totalImage: Int = R.drawable.equal_than_last,
    val nbMatchs: Int = 0,
    val playtime: Int = 0,
    val scored: Int = 0
)
