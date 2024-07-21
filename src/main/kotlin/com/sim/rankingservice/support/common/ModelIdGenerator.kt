package com.sim.rankingservice.support.common

import java.util.UUID

object ModelIdGenerator {

    fun generate(): String{
        return UUID.randomUUID().toString()
    }
}