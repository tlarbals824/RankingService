package com.sim.rankingservice.api.rank.dto

import com.sim.rankingservice.application.rank.port.inbound.GetUserRank

data class UserRankInfoResponse(
    val name: String,
    val rank: Long,
    val score: Int
) {

    companion object{
        fun from(rankInfo: GetUserRank.Result): UserRankInfoResponse{
            return UserRankInfoResponse(
                name = rankInfo.name,
                rank = rankInfo.rank,
                score = rankInfo.score
            )
        }
    }
}