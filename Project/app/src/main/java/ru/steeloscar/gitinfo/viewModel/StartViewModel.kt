package ru.steeloscar.gitinfo.viewModel

import android.content.Intent
import android.net.Uri
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import ru.steeloscar.gitinfo.BuildConfig
import ru.steeloscar.gitinfo.interfaces.StartActivityViewInterface
import ru.steeloscar.gitinfo.repository.GitInfoPreferences
import ru.steeloscar.gitinfo.repository.Repository

class StartViewModel(private val startActivityInterface: StartActivityViewInterface.View): StartActivityViewInterface.ViewModel, BaseObservable() {

    var login: String? = null
        @Bindable get

    var progressVisibility = false
        @Bindable get
        private set(value) {
            field = value
            notifyPropertyChanged(BR.progressVisibility)
        }
    var startActivityVisibility = false
        @Bindable get
        private set(value) {
            field = value
            notifyPropertyChanged(BR.startActivityVisibility)
        }

    private var repository: Repository
    private val startDisposables = CompositeDisposable()

    init {
        GitInfoPreferences(startActivityInterface.getSharedPreferences())
        repository = Repository.newInstance(startActivityInterface.getSharedPreferences())
    }

    fun checkToken(){
        if (GitInfoPreferences.getToken() != null) {
            startActivityInterface.startIntent(null)
            instance = null
        } else {
            startActivityVisibility = true
        }
    }

    override fun authorizeUser() {
        if (!login.isNullOrBlank()) {
            progressVisibility = true
            GitInfoPreferences.setLogin(login = login)
            val intent = Intent(Intent.ACTION_VIEW, repository.getAuthorizeUri(GitInfoPreferences.getLogin()))
            startActivityInterface.startIntent(intent)
        } else {
            startActivityInterface.showToast("login")
        }
    }

    override fun registerUser() {
        val intent = Intent(Intent.ACTION_VIEW, repository.getRegisterUri())
        startActivityInterface.startIntent(intent)
    }

    override fun onResume(uri: Uri?) {
        if ((uri != null) and uri.toString().startsWith(BuildConfig.REDIRECT_URI) and GitInfoPreferences.getToken().isNullOrEmpty()){
            progressVisibility = true
            val authenticateCode = uri?.getQueryParameter("code")

            startDisposables.add(repository.getTokenAPI(authenticateCode.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    GitInfoPreferences.setToken(token = response.access_token)
                    repository.updateTokenValue()
                    repository.setTokenSharedPreferences(token = response.access_token)
                    startActivityInterface.startIntent(null)
                    progressVisibility = false
                }, {
                    startActivityInterface.showToast("token")
                    progressVisibility = false
                })
            )
        } else {
            progressVisibility = false
        }
    }

    override fun onDestroy() {
        startDisposables.clear()
    }

    companion object{
        private var instance: StartViewModel? = null

        fun getInstance(startActivityInterface: StartActivityViewInterface.View): StartViewModel {
            if (instance == null) instance = StartViewModel(startActivityInterface)
            return instance as StartViewModel
        }
    }
}