package ru.steeloscar.gitinfo.repository.api


import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import ru.steeloscar.gitinfo.repository.api.body.AccessTokenBody
import ru.steeloscar.gitinfo.repository.api.model.AccessToken
import ru.steeloscar.gitinfo.repository.api.model.RepositoryCommit
import ru.steeloscar.gitinfo.repository.api.model.UserProfile
import ru.steeloscar.gitinfo.repository.api.model.UserRepository


interface GitHubAPI {

    companion object {

        private val gitHubAPI: GitHubAPI = Retrofit.Builder()
            .baseUrl("https://api.github.com")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(GitHubAPI::class.java)

        fun getInstance(): GitHubAPI = gitHubAPI
    }

    @POST("/login/oauth/access_token")
    fun getAccessToken(
        @Header("Accept") applicationType: String,
        @Body body: AccessTokenBody
    ): Observable<AccessToken>

    @GET("/user")
    fun getUser(
        @Header("Authorization") accessToken: String
    ): Observable<UserProfile>

    @GET("/user/repos?sort=updated")
    fun getRepositories(
        @Header("Authorization") accessToken: String
    ): Observable<ArrayList<UserRepository>>

    @GET("/repos/{user}/{repository}/commits")
    fun getRepositoryCommits(
        @Header("Authorization") accessToken: String,
        @Path("user") user: String,
        @Path("repository") repository: String
    ): Observable<ArrayList<RepositoryCommit>>

    @GET("/user/starred")
    fun getStars(
        @Header("Authorization") accessToken: String
    ): Observable<ArrayList<UserRepository>>

    @GET("/user/followers")
    fun getFollowers(
        @Header("Authorization") accessToken: String
    ): Observable<ArrayList<UserProfile>>

    @GET("/user/following")
    fun getFollowingUsers(
        @Header("Authorization") accessToken: String
    ): Observable<ArrayList<UserProfile>>

    @GET("/users/{login}")
    fun getUserInfo(
        @Path("login") login: String,
        @Header("Authorization") accessToken: String
    ): Observable<UserProfile>
}
