package com.sim.rankingservice.infrastructure.route.rank

import com.sim.rankingservice.application.rank.port.outbound.UserRankRepository
import com.sim.rankingservice.domain.rank.model.UserRank
import com.sim.rankingservice.domain.rank.model.UserScore
import com.sim.rankingservice.infrastructure.persistence.rank.UserRankMySQLRepository
import com.sim.rankingservice.infrastructure.redis.rank.UserRankRedisRepository
import com.sim.rankingservice.infrastructure.route.Datasource
import org.springframework.scheduling.annotation.Scheduled

class DynamicUserRankDatasourceRouteRepository(
    private val userRankMySQLRepository: UserRankMySQLRepository,
    private val userRankRedisRepository: UserRankRedisRepository,
) : UserRankRepository {

    companion object {
        val ORIGIN_DATASOURCE = Datasource.REDIS
        var DATASOURCE_SWITCH = Datasource.REDIS
    }

    @Scheduled(fixedRate = 100000)
    fun checkDatasource() {
        if (DATASOURCE_SWITCH == Datasource.MYSQL) {
            userRankRedisRepository.findAll(1)
            DATASOURCE_SWITCH = Datasource.REDIS
        }
    }

    override fun findAll(limit: Int): List<UserRank> {
        return executeWithFallback(
            targetMethod = { userRankRedisRepository.findAll(limit) },
            replaceMethod = { userRankMySQLRepository.findAll(limit) }
        )
    }

    override fun findByIdOrNull(userId: String): UserRank? {
        return executeWithFallback(
            targetMethod = { userRankRedisRepository.findByIdOrNull(userId) },
            replaceMethod = { userRankMySQLRepository.findByIdOrNull(userId) }
        )
    }

    override fun upsert(userScore: UserScore) {
        executeWithFallback(
            targetMethod = { userRankRedisRepository.upsert(userScore) },
            replaceMethod = { userRankMySQLRepository.upsert(userScore) }
        )
    }

    private inline fun <reified T> executeWithFallback(
        targetMethod: () -> T,
        replaceMethod: () -> T
    ): T {
        if (ORIGIN_DATASOURCE != DATASOURCE_SWITCH)
            return replaceMethod()

        try {
            return targetMethod()
        } catch (e: Exception) {
            DATASOURCE_SWITCH = Datasource.MYSQL
            return replaceMethod()
        }
    }

}