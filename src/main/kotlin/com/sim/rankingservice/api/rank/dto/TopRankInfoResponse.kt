package com.sim.rankingservice.api.rank.dto

import com.sim.rankingservice.application.rank.port.inbound.GetTopScoreUser

data class TopRankInfoResponse(
    val data: List<RankInfo>,
    val total: Int
) {

    data class RankInfo(
        val rank: Long,
        val name: String,
        val score: Int
    ){
    }

    companion object{
        fun from(rankInfo: GetTopScoreUser.Result): TopRankInfoResponse{
            return TopRankInfoResponse(
                data = rankInfo.data.map {
                    RankInfo(
                        rank = it.rank,
                        name = it.name,
                        score = it.score
                    )
                },
                total = rankInfo.total
            )
        }
    }
}