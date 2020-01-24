package ru.steeloscar.gitinfo.viewModel.viewPagerFragmentsViewModel

import androidx.databinding.ObservableField
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import ru.steeloscar.gitinfo.interfaces.MainActivityViewInterface
import ru.steeloscar.gitinfo.repository.Repository
import ru.steeloscar.gitinfo.repository.api.model.UserProfile

class OverviewViewModel private constructor(private val fragmentInterface: MainActivityViewInterface.Overview){

    var visibleProgressBar = ObservableField<Boolean>(true)
        private set

    var visibleContainerData = ObservableField<Boolean>(false)
        private set


    var isRefreshing = ObservableField<Boolean>(false)
        private set

    private val repository = Repository.getInstance()
    private val disposable = CompositeDisposable()

    fun getUserProfile() {
        disposable.add(
            repository.getUserProfile()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ user ->
                    UserProfile.overviewUserProfile = user
                    fragmentInterface.setUserProfile(user)
                    visibleContainerData.set(true)
                    visibleProgressBar.set(false)
                    isRefreshing.set(false)
                }, {
                    fragmentInterface.toastInternetConnection()
                    visibleProgressBar.set(false)
                    isRefreshing.set(false)
                })
        )
    }

    fun setUserProfile(userProfile: UserProfile) {
        UserProfile.overviewUserProfile = userProfile
        fragmentInterface.setUserProfile(userProfile)
        visibleContainerData.set(true)
        visibleProgressBar.set(false)
        isRefreshing.set(false)
    }

    fun onDestroy() {
        disposable.clear()
    }

    companion object {
        private var instance: OverviewViewModel? = null

        fun newInstance(fragmentInterface: MainActivityViewInterface.Overview): OverviewViewModel {
            if (instance == null) instance = OverviewViewModel(fragmentInterface)
            return  instance as OverviewViewModel
        }

        fun getInstance(): OverviewViewModel = instance as OverviewViewModel

        fun clearData() {
            instance = null
            UserProfile.overviewUserProfile = null
        }
    }
}