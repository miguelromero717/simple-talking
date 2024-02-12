package com.miguelromero717.simpletalking.shared

class UserNotFoundException : RuntimeException {
    constructor() : super()
    constructor(message: String) : super(message)
}
