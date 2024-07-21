package com.sim.rankingservice.infrastructure.persistence.user

import com.sim.rankingservice.domain.user.model.User
import com.sim.rankingservice.infrastructure.persistence.user.jpa.UserEntity

object UserMapper {

    fun toDomain(userEntity: UserEntity): User {
        return User(
            id = userEntity.id,
            name = userEntity.name,
            email = userEntity.email,
            profileImage = userEntity.profileImage
        )
    }

    fun toEntity(user: User): UserEntity {
        return UserEntity(
            id = user.id,
            name = user.name,
            email = user.email,
            profileImage = user.profileImage
        )
    }
}