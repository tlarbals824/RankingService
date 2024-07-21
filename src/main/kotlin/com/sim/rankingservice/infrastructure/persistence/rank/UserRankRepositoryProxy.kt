package com.sim.rankingservice.infrastructure.persistence.rank

import com.sim.rankingservice.application.rank.port.outbound.UserRankRepository
import com.sim.rankingservice.domain.rank.model.UserRank
import com.sim.rankingservice.domain.rank.model.UserScore
import com.sim.rankingservice.infrastructure.persistence.rank.jpa.UserRankJpaRepository
import com.sim.rankingservice.infrastructure.persistence.rank.jpa.UserScoreEntity
import java.util.concurrent.CompletableFuture

class UserRankRepositoryProxy(
    private val userRankRepository: UserRankRepository,
    private val userRankJpaRepository: UserRankJpaRepository,
    private val userRankPersistenceScheduler: UserRankPersistenceScheduler
) : UserRankRepository {

    override fun findAll(limit: Int): List<UserRank> {
        return replaceIfDisableToRequestTargetMethod(
            targetMethod = { userRankRepository.findAll(limit) },
            replaceMethod = {
                userRankJpaRepository.findAllByOrderByScoreDesc(limit).map {
                    UserRank(
                        userScoreInfo = UserScore(it.id, it.score),
                        rank = it.rank + 1
                    )
                }
            }
        )
    }

    override fun findByIdOrNull(userId: String): UserRank? {
        return replaceIfDisableToRequestTargetMethod(
            targetMethod = { userRankRepository.findByIdOrNull(userId) },
            replaceMethod = {
                userRankJpaRepository.findByIdOrNull(userId)?.let {
                    UserRank(
                        userScoreInfo = UserScore(it.id, it.score),
                        rank = it.rank
                    )
                }
            }
        )
    }

    override fun upsert(userScore: UserScore) {
        this.replaceIfDisableToRequestTargetMethod<Unit>(
            targetMethod = {
                userRankRepository.upsert(userScore)
                userRankPersistenceScheduler.addUserScoreToQueue(
                    RankMapper.toUserRankInfo(
                        userScore.userId,
                        userScore.score
                    )
                )
            },
            replaceMethod = {
                userRankJpaRepository.upsert(
                    RankMapper.toUserRankInfo(
                        userScore.userId,
                        userScore.score
                    )
                )
            }
        )
    }


    private inline fun <reified T> replaceIfDisableToRequestTargetMethod(
        targetMethod: () -> T,
        replaceMethod: () -> T
    ): T {
        try {
            return targetMethod()
        } catch (e: Exception) {
            return replaceMethod()
        }
    }
}