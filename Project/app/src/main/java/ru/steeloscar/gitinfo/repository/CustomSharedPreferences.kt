package ru.steeloscar.gitinfo.repository

import android.content.SharedPreferences
import io.reactivex.Completable

class CustomSharedPreferences(private val sharedPreferences: SharedPreferences) {

    private val tokenKey = "token"

    fun setToken(token: String): Completable {
        sharedPreferences.edit().putString(tokenKey, token).apply()
        return Completable.complete()
    }
}