package com.sim.rankingservice.application.rank.port.inbound

interface GetTopScoreUser {

    data class Query(
        val limit: Int
    )

    data class Result(
        val data: List<TopRankUserInfo>,
        val total: Int
    )

    data class TopRankUserInfo(
        val name: String,
        val rank: Long,
        val score: Int
    )

    fun execute(query: Query): Result
}