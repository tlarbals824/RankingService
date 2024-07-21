package com.sim.rankingservice.infrastructure.persistence.user

import com.sim.rankingservice.application.user.port.outbound.UserRepository
import com.sim.rankingservice.domain.user.model.User
import com.sim.rankingservice.infrastructure.persistence.user.jpa.UserJpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class UserRepositoryImpl(
    private val userJpaRepository: UserJpaRepository
) : UserRepository{
    override fun findByIdOrNull(userId: String): User? {
        return userJpaRepository.findByIdOrNull(userId)?.let {
            UserMapper.toDomain(it)
        }
    }

    override fun save(user: User): User {
        return UserMapper.toDomain(userJpaRepository.save(UserMapper.toEntity(user)))
    }
}