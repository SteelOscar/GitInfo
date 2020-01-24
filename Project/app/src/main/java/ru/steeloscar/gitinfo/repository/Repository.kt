package ru.steeloscar.gitinfo.repository

import android.content.SharedPreferences
import android.net.Uri
import io.reactivex.Completable
import io.reactivex.Observable
import ru.steeloscar.gitinfo.BuildConfig
import ru.steeloscar.gitinfo.repository.api.GitHubAPI
import ru.steeloscar.gitinfo.repository.api.body.AccessTokenBody
import ru.steeloscar.gitinfo.repository.api.body.UpdateUserProfileBody
import ru.steeloscar.gitinfo.repository.api.model.*

class Repository(sharedPreferences: SharedPreferences) {

    private val preferences =
        CustomSharedPreferences(sharedPreferences)

    private val gitHubAPI = GitHubAPI.getAPI()
    private var token = "token ${GitInfoPreferences.getToken()}"

    fun updateTokenValue(){
        token = "token ${GitInfoPreferences.getToken()}"
    }

    fun getAuthorizeUri(username: String?): Uri {
        val login =
            if (!username.isNullOrBlank()) "&login=$username"
            else ""
        return Uri.parse("https://github.com/login/oauth/authorize?client_id=${BuildConfig.CLIENT_ID}&scope=${BuildConfig.SCOPE}&redirect_uri=${BuildConfig.REDIRECT_URI}${login}")
    }

    fun getRegisterUri(): Uri =
        Uri.parse("https://github.com/join")

    fun getTokenAPI(authenticateCode: String): Observable<AccessToken> =
        gitHubAPI.getAccessToken("https://github.com/login/oauth/access_token","application/json",
            AccessTokenBody(
                BuildConfig.CLIENT_ID,
                BuildConfig.CLIENT_SECRET,
                authenticateCode,
                BuildConfig.REDIRECT_URI
            )
        )

    fun setTokenSharedPreferences(token: String): Completable = preferences.setToken(token)

    fun getUserProfile(): Observable<UserProfile> = gitHubAPI.getUser(token)

    fun gerRepositories(): Observable<ArrayList<UserRepository>>  = gitHubAPI.getRepositories(token)

    fun getRepositoryCommits(
        userRepository: String,
        repository: String
    ): Observable<ArrayList<RepositoryCommit>> = gitHubAPI.getRepositoryCommits(token, userRepository, repository)

    fun getStars(): Observable<ArrayList<UserRepository>> =
        gitHubAPI.getStars(token)

    fun getFollowers(): Observable<ArrayList<UserProfile>> =
        gitHubAPI.getFollowers(token)

    fun getFollowerInfo(login: String): Observable<UserProfile> =
        gitHubAPI.getUserInfo(login, token)

    fun getFollowingUsers(): Observable<ArrayList<UserProfile>>  =
        gitHubAPI.getFollowingUsers(token)

    fun getFollowingUserInfo(login: String) : Observable<UserProfile> =
        gitHubAPI.getUserInfo(login, token)

    fun updateUserProfile(userData: UpdateUserProfileBody): Observable<UserProfile> =
        gitHubAPI.updateProfile(userData, token)

    companion object {
        private var instance: Repository? = null

        fun newInstance(sharedPreferences: SharedPreferences): Repository {
            instance = Repository(sharedPreferences)
            return instance as Repository
        }

        fun getInstance(): Repository = instance as Repository
    }
}