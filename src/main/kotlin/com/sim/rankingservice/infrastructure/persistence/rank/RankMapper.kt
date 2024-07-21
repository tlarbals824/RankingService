package com.sim.rankingservice.infrastructure.persistence.rank

import com.sim.rankingservice.infrastructure.persistence.rank.jpa.UserScoreEntity
import com.sim.rankingservice.infrastructure.persistence.rank.jpa.dto.UserRankInfo

object RankMapper {

    fun toUserRankInfo(
        id: String,
        score: Int
    ) = UserScoreEntity(
        id = id,
        score = score
    )


}