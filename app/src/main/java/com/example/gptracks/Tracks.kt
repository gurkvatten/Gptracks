package com.example.gptracks

class Tracks(
    val name: String,
    val country: String,
    // ... andra egenskaper för ett spår
) {
    override fun toString(): String {
        return "$name, $country"
    }

    constructor() : this("", "")
}