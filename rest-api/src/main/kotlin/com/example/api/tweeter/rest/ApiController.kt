package com.example.api.tweeter.rest

import com.example.api.tweeter.db.BookRepo
import mu.KLogging
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
// see: https://github.com/MarianoLopez/MySpringTutorial/blob/master/01-%20book-backend%20basic/src/main/kotlin/com/z/bookbackend/services/AuthorService.kt

@RestController
class TweeterApiController(
        private val bookRepo: BookRepo
) {
    companion object : KLogging()

    @GetMapping("/api/tweeter/tweets/findAll")
    fun findAll() = bookRepo.findAll().toList()


}
