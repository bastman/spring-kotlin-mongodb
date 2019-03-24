package com.example.api.tweeter.db

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant
import java.time.LocalDate
import java.util.*

@Document
data class Author(@Id val id: String? = null, val name: String, val birthDate: LocalDate)

@Document
data class Book(@Id val isbn: String, val name: String, var author: Author, val publishedYear: Int)

@Document
data class Tweet(
        @Id val id: UUID,
        val createdAt: Instant,
        val modifiedAt: Instant,
        val deletedAt: Instant?,
        val isActive: Boolean,
        val message: String
)
