package com.miguelromero717.simpletalking.shared

import jakarta.persistence.*
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
