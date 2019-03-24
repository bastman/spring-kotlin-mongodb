package com.example.api.tweeter.rest.common.response

import com.example.api.api.common.rest.response.ValidityDto
import com.example.api.tweeter.db.Tweet
import java.time.Instant
import java.util.*

data class TweetDto(
        val id: UUID,
        val createdAt: Instant,
        val modifiedAt: Instant,
        val deletedAt: Instant?,
        val isActive: Boolean,
        val message: String
)

fun Tweet.toTweetDto(): TweetDto = TweetDto(
        id = id,
        createdAt = createdAt,
        modifiedAt = modifiedAt,
        deletedAt = deletedAt,
        isActive = isActive,
        message = message
)

fun Tweet.toValidityDto(): ValidityDto = ValidityDto(isActive = isActive, deletedAt = deletedAt)


