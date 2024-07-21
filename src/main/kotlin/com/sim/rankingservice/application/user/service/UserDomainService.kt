package com.sim.rankingservice.application.user.service

import com.sim.rankingservice.domain.user.model.User


interface UserDomainService {

    fun findUserById(userId: String): User

    fun save(user: User): User
}