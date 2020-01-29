package ru.steeloscar.gitinfo.interfaces

import android.content.Intent
import android.content.SharedPreferences
import ru.steeloscar.gitinfo.repository.api.model.UserProfile
import ru.steeloscar.gitinfo.repository.api.model.UserRepository

interface MainActivityViewInterface {

    interface MainView {
        fun showToast(message: String)
        fun getSharedPreferences(): SharedPreferences
    }

    interface Overview {
        fun toastInternetConnection()
        fun setUserProfile(userProfile: UserProfile)
        fun startIntent(intent: Intent)
    }

    interface Repositories {
        fun toastInternetConnection()
        fun setUserRepositories(userRepositories: ArrayList<UserRepository>)
    }

    interface Stars {
        fun toastInternetConnection()
        fun setUserStars(userStars: ArrayList<UserRepository>)
    }

    interface Followers {
        fun toastInternetConnection()
        fun setRecyclerViewUserProfiles(userProfiles: ArrayList<UserProfile>)
    }

    interface Following {
        fun toastInternetConnection()
        fun setRecyclerViewFollowingUsers(followingUsers: ArrayList<UserProfile>)
    }
}