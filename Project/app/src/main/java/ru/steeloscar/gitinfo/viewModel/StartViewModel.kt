package ru.steeloscar.gitinfo.viewModel

import android.content.Intent
import android.net.Uri
import android.text.Editable
import android.text.TextWatcher
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import ru.steeloscar.gitinfo.BuildConfig
import ru.steeloscar.gitinfo.interfaces.StartActivityInterface
import ru.steeloscar.gitinfo.repository.StartRepository
import ru.steeloscar.gitinfo.repository.GitInfoPreferences

class StartViewModel(private val startActivityInterface: StartActivityInterface.View): StartActivityInterface.ViewModel, BaseObservable() {

    private var login: String? = null

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

    private val repository = StartRepository(startActivityInterface.getSharedPreferences())
    private val disposables = CompositeDisposable()

    init {
        GitInfoPreferences(startActivityInterface.getSharedPreferences())
        if (GitInfoPreferences.getToken() != null) {
            startActivityInterface.startIntent(null)
        } else {
            startActivityVisibility = true
        }
    }

    override fun authorizeUser() {
        if (GitInfoPreferences.getToken().isNullOrBlank()) {
            progressVisibility = true
            GitInfoPreferences.setLogin(login = login)
            val intent = Intent(Intent.ACTION_VIEW, repository.getAuthorizeUri(GitInfoPreferences.getLogin()))
            startActivityInterface.startIntent(intent)
        } else {
            startActivityInterface.showToast("${GitInfoPreferences.getLogin()} is logged. ${GitInfoPreferences.getToken()}")
        }
    }

    override fun onResume(uri: Uri?) {
        if ((uri != null) and uri.toString().startsWith(BuildConfig.REDIRECT_URI) and GitInfoPreferences.getToken().isNullOrEmpty()){
            progressVisibility = true
            val authenticateCode = uri?.getQueryParameter("code")

            disposables.add(repository.getTokenAPI(authenticateCode.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    GitInfoPreferences.setToken(token = response.access_token)
                    repository.setTokenSharedPreferences(token = response.access_token)
                    startActivityInterface.startIntent(null)
                }, { error ->
                    startActivityInterface.showToast(error.toString())
                })
            )
        }
    }

    override fun onDestroy() {
        disposables.clear()
    }

    @Bindable
    fun getLoginTextWatcher(): TextWatcher {
        return object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                //Do nothing
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //Do nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                login = s.toString()
            }
        }
    }
}