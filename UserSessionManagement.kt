package com.example.app.auth

import android.content.Context

class UserSessionManager private constructor(context: Context) {
    private val tokenStorage = TokenStorage(context)
    private var cachedToken: String? = null
    private var cachedUsername: String? = null
    private var cachedUserId: String? = null
    private var cachedClassId: String? = null

    init {
        loadFromStorage()
    }

    private fun loadFromStorage() {
        cachedToken = tokenStorage.getToken()
        cachedClassId = tokenStorage.getClassId()
        cachedToken?.let { decryptAndCacheToken(it) }
    }

    fun setToken(token: String) {
        tokenStorage.saveToken(token)
        cachedToken = token
        decryptAndCacheToken(token)
    }

    fun getToken(): String? {
        return cachedToken
    }

    fun logout() {
        tokenStorage.removeToken()
        tokenStorage.removeClassId()
        cachedToken = null
        cachedUsername = null
        cachedUserId = null
        cachedClassId = null
    }

    fun setClassId(classId: String) {
        tokenStorage.saveClassId(classId)
        cachedClassId = classId
    }

    fun getClassId(): String? {
        return cachedClassId
    }

    fun getUsername(): String? {
        return if (cachedClassId != null) cachedUsername else null
    }

    fun getUserId(): String? {
        return if (cachedClassId != null) cachedUserId else null
    }

    private fun decryptAndCacheToken(token: String) {
        val json = JwtParser.decodeJwt(token) ?: return
        cachedUsername = json.optString("username", null)
        cachedUserId = json.optString("user_id", null)
    }

    companion object {
        @Volatile
        private var INSTANCE: UserSessionManager? = null

        fun getInstance(context: Context): UserSessionManager {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: UserSessionManager(context.applicationContext).also { INSTANCE = it }
            }
        }
    }
}
