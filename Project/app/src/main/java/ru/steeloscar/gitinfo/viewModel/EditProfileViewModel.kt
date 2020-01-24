package ru.steeloscar.gitinfo.viewModel

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import ru.steeloscar.gitinfo.interfaces.EditProfileActivityViewInterface
import ru.steeloscar.gitinfo.repository.Repository
import ru.steeloscar.gitinfo.repository.api.body.UpdateUserProfileBody
import ru.steeloscar.gitinfo.repository.api.model.UserProfile
import ru.steeloscar.gitinfo.viewModel.viewPagerFragmentsViewModel.OverviewViewModel

class EditProfileViewModel(private val activityInterface: EditProfileActivityViewInterface.View): BaseObservable() {
    var userName: String? = null
        @Bindable get

    private var userEmail: String? = null

    var userBlog: String? = null
        @Bindable get

    var userCompany: String? = null
        @Bindable get

    var userLocation: String? = null
        @Bindable get

    private var userHireable: Boolean? = null

    var userBio: String? = null
        @Bindable get

    var visibilityProgressBar: Boolean = false
        @Bindable get
        private set(value) {
            field = value
            notifyPropertyChanged(BR.visibilityProgressBar)
        }

    private val repository = Repository.getInstance()
    private val disposable = CompositeDisposable()

    init {
        with(UserProfile.getUpdateUserProfile()) {
            userName = name
            userEmail = email
            userBlog = blog
            userCompany = company
            userLocation = location
            userHireable = hireable
            userBio = bio
        }
    }

    fun editProfile(){
        visibilityProgressBar = true
        val updateUserProfileBody =
            UpdateUserProfileBody(
                userName,
                userEmail,
                userBlog,
                userCompany,
                userLocation,
                userHireable,
                userBio
            )
        disposable.add(repository.updateUserProfile(updateUserProfileBody)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                activityInterface.finishActivity()
                OverviewViewModel.getInstance().setUserProfile(it)
                visibilityProgressBar = false
            }, {
                activityInterface.toastMessage()
                visibilityProgressBar = false
            })
        )
    }

    fun destroyDisposable() {
        disposable.clear()
    }
}
