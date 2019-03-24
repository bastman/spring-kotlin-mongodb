package com.example.api.tweeter.db

import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*

interface AuthorRepo:MongoRepository<Author,String>
interface BookRepo: MongoRepository<Book, String> {
    fun findByAuthorId(id:String):List<Book>
    fun findByNameRegex(name:String):List<Book>
}

interface TweetRepo:MongoRepository<Tweet,UUID>
