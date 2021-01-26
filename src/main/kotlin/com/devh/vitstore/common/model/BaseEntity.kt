package com.devh.vitstore.common.model

import com.fasterxml.jackson.annotation.JsonIgnore
import java.io.Serializable
import java.util.*
import javax.persistence.*

@MappedSuperclass
abstract class BaseEntity<T : Serializable> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    var id: T? = null

    @Column(nullable = false, updatable = false)
    @JsonIgnore
    val entityUuid: String = UUID.randomUUID().toString()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true

        if (other == null || this::class.java != other::class.java) return false

        val that = other as BaseEntity<*>

        return entityUuid == that.entityUuid
    }

    override fun hashCode(): Int {
        return Objects.hash(entityUuid)
    }
}
