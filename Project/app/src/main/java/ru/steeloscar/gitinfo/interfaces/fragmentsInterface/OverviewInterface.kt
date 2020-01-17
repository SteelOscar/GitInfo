package ru.steeloscar.gitinfo.interfaces.fragmentsInterface

import android.content.Intent
import ru.steeloscar.gitinfo.repository.api.model.UserProfile

interface OverviewInterface {

    interface View {
        fun toastInternetConnection()
        fun setUserProfile(userProfile: UserProfile)
        fun startIntent(intent: Intent)
    }

    interface ViewModel {
        fun getUserProfile()
        fun onDestroy()
    }
}