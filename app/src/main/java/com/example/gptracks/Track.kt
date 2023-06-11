package com.example.gptracks

data class Track(
    var name: String? = null,
    var country: String? = null
) {
    // Empty constructor required for Firestore deserialization
    constructor() : this(null, null)
}



