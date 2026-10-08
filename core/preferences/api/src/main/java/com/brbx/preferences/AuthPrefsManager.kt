package com.brbx.preferences

import kotlinx.coroutines.flow.Flow

interface AuthPrefsManager {

    val tokens: Flow<Pair<String, String>?>

    suspend fun saveTokens(access: String, refresh: String)
    suspend fun clearTokens()
}
