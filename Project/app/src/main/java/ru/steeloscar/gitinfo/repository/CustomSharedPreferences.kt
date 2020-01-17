package ru.steeloscar.gitinfo.repository

import android.content.SharedPreferences
import io.reactivex.Completable
import io.reactivex.Observable

class CustomSharedPreferences(private val sharedPreferences: SharedPreferences) {

    private val tokenKey = "token"

    fun getToken(): Observable<String> = Observable.just(sharedPreferences.getString(tokenKey, null))

    fun setToken(token: String): Completable {
        sharedPreferences.edit().putString(tokenKey, token).apply()
        return Completable.complete()
    }
}