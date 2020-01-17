package ru.steeloscar.gitinfo.viewModel.mainFragmentsViewModel

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import ru.steeloscar.gitinfo.interfaces.fragmentsInterface.FollowersInterface
import ru.steeloscar.gitinfo.repository.MainRepository
import ru.steeloscar.gitinfo.repository.api.model.UserProfile
import ru.steeloscar.gitinfo.view.mainActivity.adapters.FollowRecyclerAdapter

class FollowersViewModel private constructor(private val fragmentInterface: FollowersInterface.View): FollowersInterface.ViewModel, BaseObservable() {

    private var count = 0

    var visibilityFollowersData = false
        @Bindable get
        private set(value) {
            field = value
            notifyPropertyChanged(BR.visibilityFollowersData)
        }

    var visibilityFollowersProgressBar: Boolean = true
        @Bindable get
        private set(value) {
            field = value
            notifyPropertyChanged(BR.visibilityFollowersProgressBar)
        }

    var isRefreshingFollowers: Boolean = false
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.refreshingFollowers)
        }

    var visibilityEmptyFormFollowers: Boolean = false
        @Bindable get
        private set(value) {
            field = value
            notifyPropertyChanged(BR.visibilityEmptyFormFollowers)
        }

    private val repository = MainRepository.getInstance()
    private val disposable = CompositeDisposable()

    override fun getFollowers() {
        disposable.add(repository.getFollowers()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({    followersProfiles ->
                visibilityEmptyFormFollowers = if (followersProfiles.isNotEmpty()) {
                    getFollowersUsersInfo(followersProfiles)
                    false
                } else {
                    fragmentInterface.setRecyclerViewUserProfiles(followersProfiles)
                    visibilityFollowersProgressBar = false
                    isRefreshingFollowers = false
                    true
                }
            },{
                if (isRefreshingFollowers) fragmentInterface.toastInternetConnection()
                visibilityFollowersProgressBar = false
                isRefreshingFollowers = false
            })
        )
    }

    override fun onDestroy() {
        disposable.clear()
    }

    private fun getFollowersUsersInfo(followersProfiles: ArrayList<UserProfile>) {
        followersProfiles.map { userProfile ->
            disposable.add(repository.getFollowerInfo(userProfile.login.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe{
                    userProfile.setProfile(it)
                    count++
                    if (count == followersProfiles.size) {
                        fragmentInterface.setRecyclerViewUserProfiles(followersProfiles)
                        visibilityFollowersProgressBar = false
                        visibilityFollowersData = true
                        isRefreshingFollowers = false
                        count = 0
                    }
                }
            )
        }
    }

    companion object {
        private var instance: FollowersViewModel? = null
        var firstInstance = true

        fun getInstance(fragmentInterface: FollowersInterface.View): FollowersViewModel {
            if (instance == null) {
                instance = FollowersViewModel(fragmentInterface)
                return instance as FollowersViewModel
            }
            firstInstance = false
            return  instance as FollowersViewModel
        }

        fun clearData() {
            instance = null
            firstInstance = true
            FollowRecyclerAdapter.clearData()
        }
    }
}