package com.miguelromero717.simpletalking.shared

import jakarta.persistence.Column
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass
import jakarta.persistence.PrePersist
import java.time.Instant

@MappedSuperclass
abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id: Int = 0

    @Column(name = "created_at")
    open var createdAt: Instant = Instant.now()

    @PrePersist
    open fun prePersist() {
        createdAt = Instant.now()
    }
}
