package com.sim.rankingservice.infrastructure.persistence.rank.jpa

import com.sim.rankingservice.infrastructure.persistence.rank.jpa.dto.UserRankInfo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query

interface UserRankJpaRepository: JpaRepository<UserScoreEntity, String> {

    @Query("""
        select new com.sim.rankingservice.infrastructure.persistence.rank.jpa.dto.UserRankInfo(
            u.id as id,
            u.score as score,
            row_number() over (order by u.score desc) as rank
        )
        from UserScoreEntity u
        order by u.score desc
        limit :limit
    """)
    fun findAllByOrderByScoreDesc(limit: Int): List<UserRankInfo>

    @Query(value = """
        select new com.sim.rankingservice.infrastructure.persistence.rank.jpa.dto.UserRankInfo(
            u.id as id,
            u.score as score,
            row_number() over (order by u.score desc) as rank
        )
        from UserScoreEntity u
        where u.id = :id
    """)
    fun findByIdOrNull(id: String): UserRankInfo?

    @Modifying
    @Query("""
        insert into user_score_entity (id, score)
        values (:#{#userScore.id}, :#{#userScore.score})
        on duplicate key update score = score + :#{#userScore.score}
    """, nativeQuery = true)
    fun upsert(userScore: UserScoreEntity): Unit
}