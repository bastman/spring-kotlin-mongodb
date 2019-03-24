package com.example.util.mongo

import org.springframework.data.mongodb.repository.MongoRepository

class MongoDocumentNotFoundException(message: String) : RuntimeException(message)

inline fun <reified T, ID> MongoRepository<T, ID>.requireExistsById(
        id: ID, noinline mapError: (MongoDocumentNotFoundException) -> RuntimeException = { it }
) {
    if (!existsById(id)) {
        throw mapError(
                MongoDocumentNotFoundException(
                        "Document Not Found! document(collection: ${T::class.qualifiedName}, id: $id"
                )
        )
    }
}

inline fun <reified T, ID> MongoRepository<T, ID>.updateById(id: ID, entity: T): T {
    requireExistsById(id)
    return save(entity)
}

fun <T, ID> MongoRepository<T, ID>.upsert(entity: T): T = save(entity)
