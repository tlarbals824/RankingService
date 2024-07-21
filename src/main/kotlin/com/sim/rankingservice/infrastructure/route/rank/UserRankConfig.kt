package com.sim.rankingservice.infrastructure.route.rank

import com.sim.rankingservice.application.rank.port.outbound.UserRankRepository
import com.sim.rankingservice.infrastructure.persistence.rank.UserRankMySQLRepository
import com.sim.rankingservice.infrastructure.redis.rank.UserRankRedisRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary

@Configuration
class UserRankConfig(
    private val userRankRedisRepository: UserRankRedisRepository,
    private val userRankMySQLRepository: UserRankMySQLRepository
) {

    @Bean
    @Primary
    fun userRankRepository(): UserRankRepository {
        return DynamicUserRankDatasourceRouteRepository(
            userRankMySQLRepository = userRankMySQLRepository,
            userRankRedisRepository = userRankRedisRepository
        )
    }
}