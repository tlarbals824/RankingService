package com.sim.rankingservice.infrastructure.persistence.user.jpa

import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
class UserEntity(
    id: String,
    name: String,
    email: String,
    profileImage: String
) {

    @Id
    val id: String = id
    val name: String = name
    val email: String = email
    val profileImage: String = profileImage
}