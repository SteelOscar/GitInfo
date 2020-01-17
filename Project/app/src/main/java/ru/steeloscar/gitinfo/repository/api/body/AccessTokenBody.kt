package ru.steeloscar.gitinfo.repository.api.body

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AccessTokenBody (
    @SerializedName("client_id") @Expose val clientID: String,
    @SerializedName("client_secret") @Expose val clientSecret: String,
    @SerializedName("code") @Expose val code: String,
    @SerializedName("redirect_uri") @Expose val redirectUri: String
)