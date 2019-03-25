package com.example.api.tweeter.db

import com.example.api.api.common.rest.error.EntityNotFoundException
import com.example.testutils.assertions.shouldEqualRecursively
import com.example.testutils.spring.BootWebMockMvcTest
import com.example.util.mongo.updateById
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldNotEqual
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DuplicateKeyException
import org.springframework.data.repository.findByIdOrNull
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*

class TweetsRepoTest(
        @Autowired private val repo: TweetRepo,
        @Autowired private val service: TweetService
) : BootWebMockMvcTest() {

    @Test
    fun `unhappy - handle unknown id's `() {
        val unknownId: UUID = UUID.randomUUID()

        repo.findByIdOrNull(id = unknownId) shouldBe null

        assertThrows<EntityNotFoundException> { service.requireExistsById(unknownId) }
    }

    @Test
    fun `repo - crud ops - should work`() {
        val now = Instant.now()//.truncatedTo(ChronoUnit.MILLIS)
        val docId = UUID.randomUUID()
        val docNew = Tweet(
                id = docId,
                createdAt = now,
                modifiedAt = now,
                deletedAt = null,
                message = "msg-${UUID.randomUUID()}",
                comment = "comment-${UUID.randomUUID()}",
                isActive = true
        )

        repo.findByIdOrNull(id = docId) shouldBe null
        // insert
        val docInserted = repo.insert(docNew)
        docInserted shouldEqualRecursively docNew
        // inserted. reload ...
        val docLoaded = repo
                .findByIdOrNull(docId)
                .also {
                    it shouldEqualRecursively docInserted
                    it shouldEqualRecursively docNew
                }!!

        // insert again ...
        assertThrows<DuplicateKeyException> { repo.insert(docNew) }

        // update ...
        val docToBeUpdated = docLoaded.copy(
                message = "msg-modified-${UUID.randomUUID()}",
                comment = "comment-modified-${UUID.randomUUID()}",
                isActive = false,
                modifiedAt = Instant.now().truncatedTo(ChronoUnit.MILLIS),
                deletedAt = Instant.now().truncatedTo(ChronoUnit.MILLIS)
        )
        val docUpdated = repo.updateById(id = docId, entity = docToBeUpdated)
                .also {
                    it shouldEqualRecursively docToBeUpdated
                    it shouldNotEqual docInserted
                    it shouldNotEqual docNew
                }
        // updated. reload ...
        repo
                .findByIdOrNull(docId)
                .also {
                    it shouldEqualRecursively docToBeUpdated
                    it shouldEqualRecursively docUpdated
                    it shouldNotEqual docInserted
                    it shouldNotEqual docNew
                }!!

    }


}
