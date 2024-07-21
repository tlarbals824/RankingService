package com.sim.rankingservice.infrastructure.persistence.rank.jpa.dto

data class UserRankInfo(
    val id: String,
    val score: Int,
    val rank: Long
)
