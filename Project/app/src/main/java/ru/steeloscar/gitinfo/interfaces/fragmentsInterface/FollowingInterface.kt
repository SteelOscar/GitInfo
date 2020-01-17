package ru.steeloscar.gitinfo.interfaces.fragmentsInterface

import ru.steeloscar.gitinfo.repository.api.model.UserProfile

interface FollowingInterface {

    interface View {
        fun toastInternetConnection()
        fun setRecyclerViewFollowingUsers(followingUsers: ArrayList<UserProfile>)
    }

    interface ViewModel {
        fun getFollowingUsers()
        fun onDestroy()
    }
}