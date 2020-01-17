package ru.steeloscar.gitinfo.repository.api.model

data class AccessToken(
    val access_token: String,
    var scope: String,
    var token_type: String
)