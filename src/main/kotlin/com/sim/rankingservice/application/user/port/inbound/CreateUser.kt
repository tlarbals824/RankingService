package com.sim.rankingservice.application.user.port.inbound

interface CreateUser {

    data class Command(
        val name: String,
        val email: String,
        val profileImage: String
    )

    fun create(command: Command)
}