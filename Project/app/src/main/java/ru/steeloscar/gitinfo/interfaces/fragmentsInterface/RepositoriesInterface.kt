package ru.steeloscar.gitinfo.interfaces.fragmentsInterface

import ru.steeloscar.gitinfo.repository.api.model.UserRepository

interface RepositoriesInterface {

    interface View {
        fun toastInternetConnection()
        fun setUserRepositories(userRepositories: ArrayList<UserRepository>)
    }

    interface ViewModel {
        fun getRepositories()
        fun onDestroy()
    }
}