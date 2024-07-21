package com.sim.rankingservice.api.user.dto

data class UserCreateRequest(
    val name: String,
    val email: String,
    val profileImage: String
)
