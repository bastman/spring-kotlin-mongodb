package com.example.api.tweeter.db

import org.springframework.data.mongodb.repository.MongoRepository

interface AuthorRepo:MongoRepository<Author,String>
interface BookRepo: MongoRepository<Book, String> {
    fun findByAuthorId(id:String):List<Book>
    fun findByNameRegex(name:String):List<Book>
}
