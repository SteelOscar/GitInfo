package ru.steeloscar.gitinfo.interfaces

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import io.reactivex.Completable
import io.reactivex.Observable
import ru.steeloscar.gitinfo.repository.api.model.AccessToken

interface StartActivityViewInterface {

    interface View {
        fun showToast(message: String)
        fun startIntent(intent: Intent?)
        fun getSharedPreferences(): SharedPreferences
    }

    interface ViewModel {
        fun authorizeUser()
        fun registerUser()
        fun onResume(uri: Uri?)
        fun onDestroy()
    }
}