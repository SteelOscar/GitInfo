package ru.steeloscar.gitinfo.viewModel.viewPagerFragmentsViewModel

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import ru.steeloscar.gitinfo.interfaces.MainActivityViewInterface
import ru.steeloscar.gitinfo.repository.Repository
import ru.steeloscar.gitinfo.repository.api.model.UserProfile

class FollowingViewModel private constructor(private val fragmentInterface: MainActivityViewInterface.Following): BaseObservable() {

    private var count = 0

    var visibilityFollowingData = false
        @Bindable get
        private set(value) {
            field = value
            notifyPropertyChanged(BR.visibilityFollowingData)
        }

    var visibilityFollowingProgressBar: Boolean = true
        @Bindable get
        private set(value) {
            field = value
            notifyPropertyChanged(BR.visibilityFollowingProgressBar)
        }

    var isRefreshingFollowing: Boolean = false
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.refreshingFollowing)
        }

    var visibilityEmptyFormFollowing: Boolean = false
        @Bindable get
        private set(value) {
            field = value
            notifyPropertyChanged(BR.visibilityEmptyFormFollowing)
        }

    private val repository = Repository.getInstance()
    private val disposable = CompositeDisposable()

    fun getFollowingUsers() {
        disposable.add(repository.getFollowingUsers()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({    followingProfiles ->
                visibilityEmptyFormFollowing = if (followingProfiles.isNotEmpty()) {
                    getFollowingUsersInfo(followingProfiles)
                    false
                } else {
                    fragmentInterface.setRecyclerViewFollowingUsers(followingProfiles)
                    visibilityFollowingProgressBar = false
                    isRefreshingFollowing = false
                    true
                }
            },{
                if (isRefreshingFollowing) fragmentInterface.toastInternetConnection()
                visibilityFollowingProgressBar = false
                isRefreshingFollowing = false
            })
        )
    }

    fun onDestroy() {
        disposable.clear()
    }

    private fun getFollowingUsersInfo(followingUsers: ArrayList<UserProfile>) {
        followingUsers.map { userProfile ->
            disposable.add(repository.getFollowingUserInfo(userProfile.login.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe{
                    userProfile.setProfile(it)
                    count++
                    if (count == followingUsers.size) {
                        fragmentInterface.setRecyclerViewFollowingUsers(followingUsers)
                        visibilityFollowingProgressBar = false
                        visibilityFollowingData = true
                        isRefreshingFollowing = false
                        count = 0
                    }
                }
            )
        }
    }

    companion object {
        private var instance: FollowingViewModel? = null
        var firstInstance = true

        fun getInstance(fragmentInterface: MainActivityViewInterface.Following): FollowingViewModel {
            if (instance == null) {
                instance = FollowingViewModel(fragmentInterface)
                return instance as FollowingViewModel
            }
            firstInstance = false
            return  instance as FollowingViewModel
        }

        fun clearData() {
            instance = null
            firstInstance = true
        }
    }
}