package com.sim.rankingservice.application.user.port.outbound

import com.sim.rankingservice.domain.user.model.User

interface UserRepository {

    fun findByIdOrNull(userId: String): User?

    fun save(user: User): User

}