package com.sim.rankingservice.application.user.service.impl

import com.sim.rankingservice.application.user.port.outbound.UserRepository
import com.sim.rankingservice.application.user.service.UserDomainService
import com.sim.rankingservice.domain.user.model.User
import org.springframework.stereotype.Service

@Service
class UserDomainServiceImpl(
    private val userRepository: UserRepository
) : UserDomainService {
    override fun findUserById(userId: String): User {
        return userRepository.findByIdOrNull(userId)?:throw RuntimeException("user not found")
    }

    override fun save(user: User): User {
        return userRepository.save(user)
    }
}