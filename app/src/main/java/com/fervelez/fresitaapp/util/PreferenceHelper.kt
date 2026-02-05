package com.fervelez.fresitaapp.util

import android.content.Context

class PreferenceHelper(context: Context) {
    private val prefs = context.getSharedPreferences("frutas_prefs", Context.MODE_PRIVATE)

    fun saveToken(token: String) { prefs.edit().putString("token", token).apply() }
    fun getToken(): String? = prefs.getString("token", null)

    fun saveUserId(id: Int) { prefs.edit().putInt("user_id", id).apply() }
    fun getUserId(): Int = prefs.getInt("user_id", -1)

    fun saveUserName(name: String) { prefs.edit().putString("user_name", name).apply() }
    fun getUserName(): String? = prefs.getString("user_name", null)

    fun clear() { prefs.edit().clear().apply() }
}
