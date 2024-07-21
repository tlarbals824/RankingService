package com.sim.rankingservice.application.rank.service.domain.impl

import com.sim.rankingservice.application.rank.port.outbound.UserRankRepository
import com.sim.rankingservice.application.rank.service.domain.UserRankDomainService
import com.sim.rankingservice.domain.rank.model.UserRank
import com.sim.rankingservice.domain.rank.model.UserScore
import com.sim.rankingservice.support.lock.LockManager
import org.springframework.stereotype.Service

@Service
class UserRankDomainServiceImpl(
    private val userRankRepository: UserRankRepository
) : UserRankDomainService {
    override fun findTopRankUser(limit: Int): List<UserRank> {
        return userRankRepository.findAll(limit)
    }

    override fun findUserRankById(userId: String): UserRank {
        return userRankRepository.findByIdOrNull(userId) ?: throw RuntimeException("user rank not exists")
    }

    override fun addScore(userId: String, score: Int) {
        userRankRepository.upsert(UserScore(userId, score))
    }
}