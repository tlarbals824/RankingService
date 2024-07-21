package com.sim.rankingservice.infrastructure.persistence.rank

import com.sim.rankingservice.application.rank.port.outbound.UserRankRepository
import com.sim.rankingservice.domain.rank.model.UserRank
import com.sim.rankingservice.domain.rank.model.UserScore
import com.sim.rankingservice.infrastructure.persistence.rank.jpa.UserRankJpaRepository
import org.springframework.stereotype.Repository


@Repository
class UserRankMySQLRepository(
    private val userRankJpaRepository: UserRankJpaRepository
) : UserRankRepository {


    override fun findAll(limit: Int): List<UserRank> {
        return userRankJpaRepository.findAllByOrderByScoreDesc(limit).map {
            UserRank(
                userScoreInfo = UserScore(it.id, it.score),
                rank = it.rank + 1
            )
        }

    }

    override fun findByIdOrNull(userId: String): UserRank? {
        return userRankJpaRepository.findByIdOrNull(userId)?.let {
            UserRank(
                userScoreInfo = UserScore(it.id, it.score),
                rank = it.rank
            )
        }
    }

    override fun upsert(userScore: UserScore) {
        val userRankInfo = RankMapper.toUserRankInfo(userScore.userId, userScore.score)
        userRankJpaRepository.upsert(userRankInfo)
    }
}