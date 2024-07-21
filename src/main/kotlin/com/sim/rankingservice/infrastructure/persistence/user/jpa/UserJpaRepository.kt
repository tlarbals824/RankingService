package com.sim.rankingservice.infrastructure.persistence.user.jpa

import org.springframework.data.jpa.repository.JpaRepository

interface UserJpaRepository:JpaRepository<UserEntity, String> {
}