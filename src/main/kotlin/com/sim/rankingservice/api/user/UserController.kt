package com.sim.rankingservice.api.user

import com.sim.rankingservice.api.user.dto.UserCreateRequest
import com.sim.rankingservice.application.user.port.inbound.CreateUser
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(
    private val createUser: CreateUser
) {

    @PostMapping("/users")
    fun createUser(
        @RequestBody request: UserCreateRequest
    ) {
        createUser.create(
            CreateUser.Command(
                name = request.name,
                email = request.email,
                profileImage = request.profileImage
            )
        )
    }

}