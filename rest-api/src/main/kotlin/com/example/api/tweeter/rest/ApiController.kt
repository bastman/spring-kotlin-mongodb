package com.example.api.tweeter.rest

import com.example.api.api.common.rest.error.EntityNotFoundException
import com.example.api.tweeter.db.Tweet
import com.example.api.tweeter.db.TweetRepo
import com.example.api.tweeter.rest.common.response.TweetDto
import com.example.api.tweeter.rest.common.response.toTweetDto
import com.example.api.tweeter.rest.common.response.toValidityDto
import mu.KLogging
import org.springframework.data.repository.findByIdOrNull
import org.springframework.web.bind.annotation.*
import java.time.Instant
import java.util.*

// see: https://github.com/MarianoLopez/MySpringTutorial/blob/master/01-%20book-backend%20basic/src/main/kotlin/com/z/bookbackend/services/AuthorService.kt

@RestController
class TweeterApiController(
        private val tweetRepo: TweetRepo
) {
    companion object : KLogging()

    @GetMapping("/api/tweeter/tweets/findAll")
    fun findAll():List<TweetDto> = tweetRepo.findAll().toList().map { it.toTweetDto() }

    @PutMapping("/api/tweeter/tweets")
    fun insert(@RequestBody req: TweetCreateRequest):TweetDto = req
            .toDocument(id = UUID.randomUUID(), now = Instant.now())
            .let(tweetRepo::insert)
            .toTweetDto()

    @GetMapping("/api/tweeter/tweets/{id}")
    fun getById(@PathVariable id: UUID):TweetDto = tweetRepo
            .findByIdOrNull(id = id)
            .requireExists(id = id)
            .requireIsActive()
            .toTweetDto()
}


data class TweetCreateRequest(val message: String)

fun TweetCreateRequest.toDocument(id: UUID, now: Instant): Tweet =
        Tweet(id = id, createdAt = now, modifiedAt = now, deletedAt = null, isActive = true, message = message)

fun Tweet?.requireExists(id: UUID): Tweet =
        when (this) {
            null -> throw EntityNotFoundException("Not Found! document(collection: Tweet, id: $id")
            else -> this
        }

fun Tweet.requireIsActive(): Tweet =
        when (isActive) {
            true -> this
            false -> throw EntityNotFoundException(
                    "Not Found! document(collection: Tweet, id: $id) - soft deleted. validity: ${this.toValidityDto()}"
            )
        }
