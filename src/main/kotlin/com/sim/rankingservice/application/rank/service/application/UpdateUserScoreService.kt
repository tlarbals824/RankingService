package com.sim.rankingservice.application.rank.service.application

import com.sim.rankingservice.application.rank.port.inbound.UpdateUserScore
import com.sim.rankingservice.application.rank.service.domain.UserRankDomainService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UpdateUserScoreService(
    private val userRankDomainService: UserRankDomainService
) : UpdateUserScore {

    @Transactional
    override fun execute(command: UpdateUserScore.Command) {
        userRankDomainService.addScore(
            userId =  command.userId,
            score = command.addScore
        )
    }
}