package com.brbx.preferences

import kotlinx.coroutines.flow.Flow

interface AuthPrefsManager {

    val accessToken: Flow<String?>
    val refreshToken: Flow<String?>

    suspend fun saveAccessToken(token: String)
    suspend fun saveRefreshToken(token: String)
}