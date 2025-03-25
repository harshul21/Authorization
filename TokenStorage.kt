package com.example.app.auth

import android.content.Context
import android.content.SharedPreferences

class TokenStorage(context: Context) {
    companion object {
        private const val PREF_NAME = "auth_prefs"
        private const val TOKEN_KEY = "jwt_token"
        private const val CLASS_ID_KEY = "class_id"
    }

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        sharedPreferences.edit().putString(TOKEN_KEY, token).apply()
    }

    fun getToken(): String? {
        return sharedPreferences.getString(TOKEN_KEY, null)
    }

    fun removeToken() {
        sharedPreferences.edit().remove(TOKEN_KEY).apply()
    }

    fun saveClassId(classId: String) {
        sharedPreferences.edit().putString(CLASS_ID_KEY, classId).apply()
    }

    fun getClassId(): String? {
        return sharedPreferences.getString(CLASS_ID_KEY, null)
    }

    fun removeClassId() {
        sharedPreferences.edit().remove(CLASS_ID_KEY).apply()
    }
}
