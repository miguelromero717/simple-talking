package com.miguelromero717.simpletalking.shared.async

abstract class RabbitMQConsumer<T> {
    abstract fun consume(payload: T)
}
