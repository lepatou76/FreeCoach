package com.example.freecoach

data class Match(
    val id: Int,
    val teamHome: String,
    val teamOutside: String,
    val goalTeamHome: Int,
    val goalTeamOutside: Int,
    val challengeResult: Boolean,
    val matchReport: String = "Points à travailler"
)
