package com.sim.rankingservice.infrastructure.persistence.rank.jpa

import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
class UserScoreEntity(
    id: String,
    score: Int
) {
    @Id
    val id: String = id
    val score: Int = score
}