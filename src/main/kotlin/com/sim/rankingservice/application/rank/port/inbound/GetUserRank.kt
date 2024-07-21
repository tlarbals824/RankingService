package com.sim.rankingservice.application.rank.port.inbound

interface GetUserRank {

    data class Query(
        val userId: String
    )

    data class Result(
        val name: String,
        val rank: Long,
        val score: Int
    )

    fun execute(query: Query): Result
}