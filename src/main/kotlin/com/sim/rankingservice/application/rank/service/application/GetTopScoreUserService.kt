package com.sim.rankingservice.application.rank.service.application

import com.sim.rankingservice.application.rank.port.inbound.GetTopScoreUser
import com.sim.rankingservice.application.rank.service.domain.UserRankDomainService
import com.sim.rankingservice.application.user.port.inbound.GetUserMetaData
import org.springframework.stereotype.Service

@Service
class GetTopScoreUserService(
    private val userRankDomainService: UserRankDomainService,
    private val getUserMetaData: GetUserMetaData
) : GetTopScoreUser {

    override fun execute(query: GetTopScoreUser.Query): GetTopScoreUser.Result {
        val userRanks = userRankDomainService.findTopRankUser(query.limit)
        return GetTopScoreUser.Result(
            userRanks.map {
                val user = getUserMetaData.getById(it.userScoreInfo.userId)
                GetTopScoreUser.TopRankUserInfo(
                    user.name,
                    it.rank,
                    it.userScoreInfo.score
                )
            },
            userRanks.size
        );

    }
}