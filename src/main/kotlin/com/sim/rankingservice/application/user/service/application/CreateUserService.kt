package com.sim.rankingservice.application.user.service.application

import com.sim.rankingservice.application.user.port.inbound.CreateUser
import com.sim.rankingservice.application.user.service.UserDomainService
import com.sim.rankingservice.domain.user.model.User
import org.springframework.stereotype.Service

@Service
class CreateUserService(
    private val userDomainService: UserDomainService
) : CreateUser {
    override fun create(command: CreateUser.Command) {
        val user = userDomainService.save(
            User(
                name = command.name,
                email = command.email,
                profileImage = command.profileImage
            )
        )
    }
}