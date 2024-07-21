package com.sim.rankingservice.domain.user.model

import com.sim.rankingservice.support.common.ModelIdGenerator

class User(
    val id: String = ModelIdGenerator.generate(),
    val name: String,
    val email: String,
    val profileImage: String
) {


}