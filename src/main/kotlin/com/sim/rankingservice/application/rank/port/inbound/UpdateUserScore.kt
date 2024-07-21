package com.sim.rankingservice.application.rank.port.inbound

interface UpdateUserScore {

    data class Command(
        val userId: String,
        val addScore: Int
    )

    fun execute(command: Command)
}