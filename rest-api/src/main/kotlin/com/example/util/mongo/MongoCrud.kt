package com.example.util.mongo

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.repository.NoRepositoryBean
import java.util.*

interface MongoCrudDocument {
    fun docId(): UUID
}

@NoRepositoryBean
interface MongoCrudRepo<T : MongoCrudDocument> : MongoRepository<T, UUID>

inline fun <reified T : MongoCrudDocument> MongoCrudRepo<T>.requireExistsById(
        id: UUID, noinline mapError: (MongoDocumentNotFoundException) -> RuntimeException = { it }
) {
    if (!existsById(id)) {
        throw mapError(
                MongoDocumentNotFoundException(
                        "Document Not Found! document(collection: ${T::class.qualifiedName}, id: $id"
                )
        )
    }
}


inline fun <reified T : MongoCrudDocument> MongoCrudRepo<T>.update(entity: T): T {
    requireExistsById(id = entity.docId())
    return save(entity)
}

fun <T : MongoCrudDocument> MongoCrudRepo<T>.upsert(entity: T): T = save(entity)
