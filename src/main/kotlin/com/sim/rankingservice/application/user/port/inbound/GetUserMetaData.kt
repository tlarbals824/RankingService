package com.sim.rankingservice.application.user.port.inbound

import com.sim.rankingservice.domain.user.model.User

interface GetUserMetaData {

    fun getById(userId: String): User
}