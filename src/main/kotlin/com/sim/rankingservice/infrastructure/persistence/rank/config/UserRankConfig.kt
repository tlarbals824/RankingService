package com.sim.rankingservice.infrastructure.persistence.rank.config

import com.sim.rankingservice.application.rank.port.outbound.UserRankRepository
import com.sim.rankingservice.infrastructure.persistence.rank.UserRankPersistenceScheduler
import com.sim.rankingservice.infrastructure.persistence.rank.UserRankRepositoryProxy
import com.sim.rankingservice.infrastructure.persistence.rank.jpa.UserRankJpaRepository
import com.sim.rankingservice.infrastructure.redis.rank.UserRankRepositoryImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary

@Configuration
class UserRankConfig (
    private val userRankRepositoryImpl: UserRankRepositoryImpl,
    private val userRankJpaRepository: UserRankJpaRepository,
    private val userRankPersistenceScheduler: UserRankPersistenceScheduler
){

    @Bean
    @Primary
    fun userRankRepository(): UserRankRepository {
        return UserRankRepositoryProxy(
            userRankRepositoryImpl,
            userRankJpaRepository,
            userRankPersistenceScheduler
        )
    }
}