package com.brbx.domain.model.enums

enum class RequestException {
    // --- Http Errors (4xx, 5xx) ---
    Conflict,
    TooManyRequests,
    PayloadTooLarge,
    ServerError,
    Unauthorized,
    // --- Local / Connectivity Errors ---
    RequestTimeout,
    Internet,
    // --- Fallback ---
    Unknown,
    // --- Extra ---
    CsrfAttack
}