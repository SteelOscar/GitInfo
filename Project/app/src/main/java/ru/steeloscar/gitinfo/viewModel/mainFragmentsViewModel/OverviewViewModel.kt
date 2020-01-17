package ru.steeloscar.gitinfo.viewModel.mainFragmentsViewModel

import androidx.databinding.ObservableField
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import ru.steeloscar.gitinfo.interfaces.fragmentsInterface.OverviewInterface
import ru.steeloscar.gitinfo.repository.MainRepository
import ru.steeloscar.gitinfo.repository.api.model.UserProfile

class OverviewViewModel private constructor(private val fragmentInterface: OverviewInterface.View): OverviewInterface.ViewModel {

    var visibleProgressBar = ObservableField<Boolean>(true)
        private set

    var visibleContainerData = ObservableField<Boolean>(false)
        private set


    var isRefreshing = ObservableField<Boolean>(false)
        private set

    private val repository = MainRepository.getInstance()
    private val disposable = CompositeDisposable()

    override fun getUserProfile() {
        disposable.add(
            repository.getUserProfile()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ user ->
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

    override fun onDestroy() {
        disposable.clear()
    }

    companion object {
        private var instance: OverviewViewModel? = null

        fun getInstance(fragmentInterface: OverviewInterface.View): OverviewViewModel {
            if (instance == null) instance = OverviewViewModel(fragmentInterface)
            return  instance as OverviewViewModel
        }

        fun clearData() {
            instance = null
            UserProfile.overviewUserProfile = null
        }
    }
}