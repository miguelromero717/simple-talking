package com.miguelromero717.simpletalking.messages

import com.miguelromero717.simpletalking.shared.BaseEntity
import com.miguelromero717.simpletalking.users.User
import jakarta.persistence.Entity
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table

@Entity
@Table(name = "messages")
class Message(
    @OneToOne
    @JoinColumn(name = "sender_id")
    var sender: User,
    @OneToOne
    @JoinColumn(name = "receiver_id")
    var receiver: User,
    var payload: String,
) : BaseEntity()
