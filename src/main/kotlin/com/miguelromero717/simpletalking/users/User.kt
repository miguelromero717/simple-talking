package com.miguelromero717.simpletalking.users

import com.miguelromero717.simpletalking.shared.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "users")
class User(
    var nickname: String
): BaseEntity()
