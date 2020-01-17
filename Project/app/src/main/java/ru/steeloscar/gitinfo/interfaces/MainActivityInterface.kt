package ru.steeloscar.gitinfo.interfaces

import android.content.Intent
import android.content.SharedPreferences
import io.reactivex.Completable
import io.reactivex.Observable
import ru.steeloscar.gitinfo.repository.api.model.RepositoryCommit
import ru.steeloscar.gitinfo.repository.api.model.UserProfile
import ru.steeloscar.gitinfo.repository.api.model.UserRepository

interface MainActivityInterface {

    interface View {
        fun showToast(message: String)
        fun startIntent(intent: Intent?)
        fun getSharedPreferences(): SharedPreferences
    }

    interface ViewModel {
        fun signOut()
    }

    interface Repository {
        fun setTokenSharedPreferences(token: String): Completable
        fun getUserProfile(): Observable<UserProfile>
        fun gerRepositories(): Observable<ArrayList<UserRepository>>
        fun getRepositoryCommits(userRepository: String, repository: String): Observable<ArrayList<RepositoryCommit>>
        fun getStars(): Observable<ArrayList<UserRepository>>
        fun getFollowers(): Observable<ArrayList<UserProfile>>
        fun getFollowerInfo(login: String): Observable<UserProfile>
        fun getFollowingUsers(): Observable<ArrayList<UserProfile>>
        fun getFollowingUserInfo(login: String): Observable<UserProfile>
    }
}