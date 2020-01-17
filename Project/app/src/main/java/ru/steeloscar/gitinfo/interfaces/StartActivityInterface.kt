package ru.steeloscar.gitinfo.interfaces

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import io.reactivex.Completable
import io.reactivex.Observable
import ru.steeloscar.gitinfo.repository.api.model.AccessToken

interface StartActivityInterface {

    interface View {
        fun showToast(message: String)
        fun startIntent(intent: Intent?)
        fun getSharedPreferences(): SharedPreferences
    }

    interface ViewModel {
        fun authorizeUser()
        fun onResume(uri: Uri?)
        fun onDestroy()
    }

    interface Repository {
        fun getAuthorizeUri(username: String?): Uri
        fun getTokenAPI(authenticateCode: String): Observable<AccessToken>
        fun setTokenSharedPreferences(token: String): Completable
    }

}