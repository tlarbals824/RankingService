package com.sim.rankingservice.api.rank

import com.sim.rankingservice.api.rank.dto.TopRankInfoResponse
import com.sim.rankingservice.api.rank.dto.UserRankInfoResponse
import com.sim.rankingservice.application.rank.port.inbound.GetTopScoreUser
import com.sim.rankingservice.application.rank.port.inbound.GetUserRank
import com.sim.rankingservice.application.rank.port.inbound.UpdateUserScore
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/scores")
class RankController(
    private val getTopScoreUser: GetTopScoreUser,
    private val getUserRank: GetUserRank,
    private val updateUserScore: UpdateUserScore
) {

    @GetMapping
    fun getTopScores(
        @RequestParam limit: Int
    ): TopRankInfoResponse {
        return TopRankInfoResponse.from(
            rankInfo = getTopScoreUser.execute(GetTopScoreUser.Query(limit))
        )
    }

    @GetMapping("/{userId}")
    fun getUserRank(
        @PathVariable userId: String
    ): UserRankInfoResponse {
        return UserRankInfoResponse.from(
            rankInfo = getUserRank.execute(GetUserRank.Query(userId))
        )
    }

    @PostMapping
    fun updateUserScore(
        @RequestBody request: UpdateUserScore.Command
    ) {
        updateUserScore.execute(UpdateUserScore.Command(request.userId, request.addScore))
    }

}