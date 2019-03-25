package com.example.api.tweeter.db

import com.example.util.mongo.MongoCrudDocument
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.TypeAlias
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant
import java.util.*

@Document(collection = MongoCollections.TWEETS_COLLECTION_NAME)
@TypeAlias(MongoCollections.TWEETS_DOCUMENT_TYPE)
data class Tweet(
        @Id val id: UUID,
        val createdAt: Instant,
        val modifiedAt: Instant,
        val deletedAt: Instant?,
        val isActive: Boolean,
        val message: String,
        val comment: String
) : MongoCrudDocument {
    override fun docId(): UUID = id
}
