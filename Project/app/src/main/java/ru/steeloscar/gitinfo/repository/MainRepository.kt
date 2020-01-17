package ru.steeloscar.gitinfo.repository

import android.content.SharedPreferences
import io.reactivex.Completable
import io.reactivex.Observable
import ru.steeloscar.gitinfo.interfaces.MainActivityInterface
import ru.steeloscar.gitinfo.repository.api.GitHubAPI
import ru.steeloscar.gitinfo.repository.api.model.RepositoryCommit
import ru.steeloscar.gitinfo.repository.api.model.UserProfile
import ru.steeloscar.gitinfo.repository.api.model.UserRepository

class MainRepository(sharedPreferences: SharedPreferences): MainActivityInterface.Repository {

    private val preferences =
        CustomSharedPreferences(sharedPreferences)

    private val gitHubAPI = GitHubAPI.getInstance()
    private val token = "token ${GitInfoPreferences.getToken()}"

    override fun setTokenSharedPreferences(token: String): Completable {
        GitInfoPreferences.setToken(null)
        return preferences.setToken(token)
    }

    override fun getUserProfile(): Observable<UserProfile> = gitHubAPI.getUser(token)

    override fun gerRepositories(): Observable<ArrayList<UserRepository>>  = gitHubAPI.getRepositories(token)

    override fun getRepositoryCommits(
        userRepository: String,
        repository: String
    ): Observable<ArrayList<RepositoryCommit>> = gitHubAPI.getRepositoryCommits(token, userRepository, repository)

    override fun getStars(): Observable<ArrayList<UserRepository>> = gitHubAPI.getStars(token)

    override fun getFollowers(): Observable<ArrayList<UserProfile>> = gitHubAPI.getFollowers(token)

    override fun getFollowerInfo(login: String): Observable<UserProfile> = gitHubAPI.getUserInfo(login, token)

    override fun getFollowingUsers(): Observable<ArrayList<UserProfile>>  =  gitHubAPI.getFollowingUsers(token)

    override fun getFollowingUserInfo(login: String): Observable<UserProfile> = gitHubAPI.getUserInfo(login, token)

    companion object {
        private var instance: MainRepository? = null

        fun newInstance(sharedPreferences: SharedPreferences): MainRepository {
            instance = MainRepository(sharedPreferences)
            return instance as MainRepository
        }

        fun getInstance(): MainRepository = instance as MainRepository
    }
}