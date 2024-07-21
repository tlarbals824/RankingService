package com.sim.rankingservice.application.rank.port.outbound

import com.sim.rankingservice.domain.rank.model.UserRank
import com.sim.rankingservice.domain.rank.model.UserScore

interface UserRankRepository {

    fun findAll(limit: Int): List<UserRank>

    fun findByIdOrNull(userId: String): UserRank?

    fun upsert(userScore: UserScore)

}