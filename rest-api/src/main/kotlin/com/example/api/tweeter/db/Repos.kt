package com.example.api.tweeter.db

import com.example.api.api.common.rest.error.EntityNotFoundException
import com.example.api.tweeter.rest.common.response.toValidityDto
import com.example.util.mongo.requireExistsById
import com.example.util.mongo.updateById
import com.example.util.mongo.upsert
import mu.KLogging
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import java.util.*


interface TweetRepo : MongoRepository<Tweet, UUID>

@Component
class TweetService(private val repo: TweetRepo) {
    companion object : KLogging()

    fun existsById(id: UUID): Boolean = repo.existsById(id)
    fun requireExistsById(id: UUID): Unit = repo
            .requireExistsById(id) { EntityNotFoundException(message = "${it.message}") }

    fun upsert(tweet: Tweet) = repo
            .upsert(tweet)
            .also { logger.info { "DB: Upsert Tweet: $tweet" } }

    fun insert(tweet: Tweet): Tweet = repo
            .insert(tweet)
            .also { logger.info { "DB: Insert Tweet: $tweet" } }

    fun update(tweet: Tweet): Tweet = repo
            .updateById(id = tweet.id, entity = tweet)
            .also { logger.info { "DB: UPDATE Tweet: $tweet" } }

    fun findById(id: UUID): Tweet? = repo.findByIdOrNull(id = id)

    fun getById(id: UUID): Tweet = findById(id = id)
            .requireExists(id = id)

}

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
