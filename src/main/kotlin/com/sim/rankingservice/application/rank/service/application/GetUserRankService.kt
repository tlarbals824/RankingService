package com.sim.rankingservice.application.rank.service.application

import com.sim.rankingservice.application.rank.port.inbound.GetUserRank
import com.sim.rankingservice.application.rank.service.domain.UserRankDomainService
import com.sim.rankingservice.application.user.port.inbound.GetUserMetaData
import org.springframework.stereotype.Service

@Service
class GetUserRankService(
    private val userRankDomainService: UserRankDomainService,
    private val userMetaData: GetUserMetaData
) : GetUserRank {
    override fun execute(query: GetUserRank.Query): GetUserRank.Result {
        val user = userMetaData.getById(query.userId)
        val userRank = userRankDomainService.findUserRankById(query.userId)
        return GetUserRank.Result(
            name= user.name,
            rank =  userRank.rank,
            score = userRank.userScoreInfo.score
        )
    }
}