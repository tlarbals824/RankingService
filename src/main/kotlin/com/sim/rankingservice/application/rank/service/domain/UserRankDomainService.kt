package com.sim.rankingservice.application.rank.service.domain

import com.sim.rankingservice.domain.rank.model.UserRank

interface UserRankDomainService {

    fun findTopRankUser(limit: Int): List<UserRank>

    fun findUserRankById(userId: String): UserRank

    fun addScore(userId: String, score: Int)
}