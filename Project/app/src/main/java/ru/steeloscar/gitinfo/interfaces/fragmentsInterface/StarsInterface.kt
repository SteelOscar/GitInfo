package ru.steeloscar.gitinfo.interfaces.fragmentsInterface

import ru.steeloscar.gitinfo.repository.api.model.UserRepository

interface StarsInterface {

    interface View {
        fun toastInternetConnection()
        fun setUserStars(userStars: ArrayList<UserRepository>)
    }

    interface ViewModel {
        fun getStars()
        fun onDestroy()
    }
}