package ru.steeloscar.gitinfo.repository

import android.content.SharedPreferences

class GitInfoPreferences(preferences: SharedPreferences) {

    init {
        with(preferences.getString("token", null)) {
            if (!this.isNullOrBlank()) token = this
        }
    }

    companion object {
        private var login: String? = null
        private var token: String? = null

        fun getLogin() = login

        fun getToken() = token

        fun setLogin(login: String?) {
            this.login = login
        }

        fun setToken(token: String?) {
            this.token = token
        }
    }
}