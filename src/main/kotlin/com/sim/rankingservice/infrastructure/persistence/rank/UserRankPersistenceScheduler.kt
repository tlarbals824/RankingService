package com.sim.rankingservice.infrastructure.persistence.rank

import com.sim.rankingservice.infrastructure.persistence.rank.jpa.UserRankJpaRepository
import com.sim.rankingservice.infrastructure.persistence.rank.jpa.UserScoreEntity
import com.sim.rankingservice.infrastructure.persistence.user.jpa.UserJpaRepository
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.concurrent.LinkedBlockingQueue

@Component
class UserRankPersistenceScheduler(
    private val userRankJpaRepository: UserRankJpaRepository
) {

    private val userScoreQueue = LinkedBlockingQueue<UserScoreEntity>()


    @Transactional
    @Scheduled(fixedRate = 100000)
    fun persistUserScores() {
        println("Persisting user scores..., ${userScoreQueue.size}")
        while (userScoreQueue.isNotEmpty()) {
            val userScoreEntity = userScoreQueue.poll()
            userRankJpaRepository.upsert(userScoreEntity)
        }
    }

    fun addUserScoreToQueue(userScoreEntity: UserScoreEntity) {
        userScoreQueue.add(userScoreEntity)
    }
}