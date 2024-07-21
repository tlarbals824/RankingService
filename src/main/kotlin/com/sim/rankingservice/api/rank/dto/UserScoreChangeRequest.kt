package com.sim.rankingservice.api.rank.dto

data class UserScoreChangeRequest(
    val userId: String,
    val score: Int
)
