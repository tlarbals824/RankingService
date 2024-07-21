package com.sim.rankingservice.application.user.service.application

import com.sim.rankingservice.application.user.port.inbound.GetUserMetaData
import com.sim.rankingservice.application.user.service.UserDomainService
import com.sim.rankingservice.domain.user.model.User
import org.springframework.stereotype.Service

@Service
class GetUserMetaDataService(
    private val userDomainService: UserDomainService
) : GetUserMetaData{


    override fun getById(userId: String): User {
        return userDomainService.findUserById(userId)
    }
}