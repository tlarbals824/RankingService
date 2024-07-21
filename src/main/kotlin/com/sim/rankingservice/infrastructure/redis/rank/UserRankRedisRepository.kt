package com.sim.rankingservice.infrastructure.redis.rank

import com.sim.rankingservice.application.rank.port.outbound.UserRankRepository
import com.sim.rankingservice.domain.rank.model.UserRank
import com.sim.rankingservice.domain.rank.model.UserScore
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository

@Repository
class UserRankRedisRepository(
    private val redisTemplate: RedisTemplate<String, String>
) : UserRankRepository {
    override fun findAll(limit: Int): List<UserRank> {
        return redisTemplate.opsForZSet().reverseRangeWithScores(REDIS_KEY, 0, limit.toLong())?.map {
            val rank = redisTemplate.opsForZSet().reverseRank(REDIS_KEY, it.value!!) ?: 0
            return@map UserRank(
                userScoreInfo = UserScore(it.value!!, it.score!!.toInt()),
                rank = rank + 1
            )
        } ?: emptyList()
    }

    override fun findByIdOrNull(userId: String): UserRank? {
        val rank = redisTemplate.opsForZSet().reverseRank(REDIS_KEY, userId) ?: return null
        val score = redisTemplate.opsForZSet().score(REDIS_KEY, userId)?.toInt() ?: return null
        return UserRank(
            userScoreInfo = UserScore(userId, score),
            rank = rank + 1
        )
    }

    override fun upsert(userScore: UserScore) {
        redisTemplate.opsForZSet().score(REDIS_KEY, userScore.userId)?.let {
            redisTemplate.opsForZSet().incrementScore(REDIS_KEY, userScore.userId, userScore.score.toDouble())
        } ?: redisTemplate.opsForZSet().add(REDIS_KEY, userScore.userId, userScore.score.toDouble())
    }

    companion object {
        const val REDIS_KEY = "user_rank"
    }
}