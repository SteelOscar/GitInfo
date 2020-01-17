package ru.steeloscar.gitinfo.interfaces.fragmentsInterface

import ru.steeloscar.gitinfo.repository.api.model.UserProfile

interface FollowersInterface {

    interface View {
        fun toastInternetConnection()
        fun setRecyclerViewUserProfiles(userProfiles: ArrayList<UserProfile>)
    }

    interface ViewModel {
        fun getFollowers()
        fun onDestroy()
    }
}