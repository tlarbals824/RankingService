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
            userScoreQueue.poll().let {
                userRankJpaRepository.findByIdOrNull(it.id)?.let { existScore ->
                    userRankJpaRepository.save(
                        UserScoreEntity(
                            id = it.id,
                            score = it.score + existScore.score
                        )
                    )
                } ?: userRankJpaRepository.save(
                    UserScoreEntity(
                        id = it.id,
                        score = it.score
                    )
                )
            }
        }
    }

    fun addUserScoreToQueue(userScoreEntity: UserScoreEntity) {
        userScoreQueue.add(userScoreEntity)
    }
}