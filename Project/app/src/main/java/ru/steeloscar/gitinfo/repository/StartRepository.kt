package ru.steeloscar.gitinfo.repository

import android.net.Uri
import io.reactivex.Completable
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.steeloscar.gitinfo.BuildConfig
import ru.steeloscar.gitinfo.interfaces.StartActivityInterface
import ru.steeloscar.gitinfo.repository.api.body.AccessTokenBody
import ru.steeloscar.gitinfo.repository.api.GitHubAPI
import ru.steeloscar.gitinfo.repository.api.model.AccessToken

class StartRepository(sharedPreferences: android.content.SharedPreferences): StartActivityInterface.Repository {
    private val preferences =
        CustomSharedPreferences(sharedPreferences)

    override fun getAuthorizeUri(username: String?): Uri {
        val login =
            if (!username.isNullOrBlank()) "&login=$username"
            else ""
       return Uri.parse("https://github.com/login/oauth/authorize?client_id=${BuildConfig.CLIENT_ID}&scope=${BuildConfig.SCOPE}&redirect_uri=${BuildConfig.REDIRECT_URI}${login}")
    }

    override fun getTokenAPI(authenticateCode: String): Observable<AccessToken> {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://github.com")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        return retrofit.create(GitHubAPI::class.java).getAccessToken("application/json",
            AccessTokenBody(
                BuildConfig.CLIENT_ID,
                BuildConfig.CLIENT_SECRET,
                authenticateCode,
                BuildConfig.REDIRECT_URI
            )
        )
    }

    override fun setTokenSharedPreferences(token: String): Completable = preferences.setToken(token)
}
