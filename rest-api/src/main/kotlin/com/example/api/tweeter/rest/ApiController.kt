package com.example.api.tweeter.rest

import com.example.api.tweeter.db.Tweet
import com.example.api.tweeter.db.TweetService
import com.example.api.tweeter.db.requireIsActive
import com.example.api.tweeter.rest.common.response.TweetDto
import com.example.api.tweeter.rest.common.response.toTweetDto
import mu.KLogging
import org.springframework.web.bind.annotation.*
import java.time.Instant
import java.util.*


@RestController
class TweeterApiController(
        private val tweetService: TweetService
) {
    companion object : KLogging()

    @GetMapping("/api/tweeter/tweets/{id}")
    fun getById(@PathVariable id: UUID): TweetDto = tweetService
            .getById(id = id)
            .requireIsActive()
            .toTweetDto()

    @PutMapping("/api/tweeter/tweets")
    fun insert(@RequestBody payload: TweetCreatePayload): TweetDto = payload
            .toDocument(id = UUID.randomUUID(), now = Instant.now())
            .let(tweetService::insert)
            .toTweetDto()

    @PostMapping("/api/tweeter/tweets/{id}")
    fun update(
            @PathVariable id: UUID,
            @RequestBody payload: TweetUpdatePayload
    ): TweetDto = tweetService
            .getById(id = id)
            .requireIsActive()
            .copy(
                    modifiedAt = Instant.now(),
                    message = payload.message,
                    comment = payload.comment
            )
            .let(tweetService::update)
            .toTweetDto()

    @DeleteMapping("/api/tweeter/tweets/{id}")
    fun softDelete(@PathVariable id: UUID): TweetDto = tweetService
            .getById(id = id)
            .requireIsActive()
            .copy(isActive = false, deletedAt = Instant.now())
            .let(tweetService::update)
            .toTweetDto()

    @PostMapping("/api/tweeter/tweets/{id}/restore")
    fun softRestore(@PathVariable id: UUID): TweetDto = tweetService
            .getById(id = id)
            .copy(isActive = true, deletedAt = null)
            .let(tweetService::update)
            .toTweetDto()

    @GetMapping("/api/tweeter/tweets/findAll")
    fun findAll(): List<TweetDto> = tweetService
            .findAll()
            .map { it.toTweetDto() }

}

data class TweetCreatePayload(val message: String)
data class TweetUpdatePayload(val message: String, val comment: String)

fun TweetCreatePayload.toDocument(id: UUID, now: Instant): Tweet =
        Tweet(
                id = id,
                createdAt = now,
                modifiedAt = now,
                deletedAt = null,
                isActive = true,
                message = message,
                comment = ""
        )




