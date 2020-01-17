package ru.steeloscar.gitinfo.viewModel.mainFragmentsViewModel

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import ru.steeloscar.gitinfo.interfaces.fragmentsInterface.StarsInterface
import ru.steeloscar.gitinfo.repository.MainRepository
import ru.steeloscar.gitinfo.view.mainActivity.adapters.StarsRecyclerAdapter

class StarsViewModel private constructor(private val fragmentInterface: StarsInterface.View): StarsInterface.ViewModel, BaseObservable() {

    var visibilityStarsData = false
        @Bindable get
        private set(value) {
            field = value
            notifyPropertyChanged(BR.visibilityStarsData)
        }

    var visibilityStarsProgressBar: Boolean = true
        @Bindable get
        private set(value) {
            field = value
            notifyPropertyChanged(BR.visibilityStarsProgressBar)
        }

    var isRefreshingStars: Boolean = false
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.refreshingStars)
        }

    var visibilityEmptyFormStars: Boolean = false
        @Bindable get
        private set(value) {
            field = value
            notifyPropertyChanged(BR.visibilityEmptyFormStars)
        }

    private val repository = MainRepository.getInstance()
    private val disposable = CompositeDisposable()

    override fun getStars() {
        disposable.add(repository.getStars()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ arrayListStars ->
                visibilityEmptyFormStars = if (arrayListStars.isNotEmpty()) {
                    fragmentInterface.setUserStars(arrayListStars)
                    visibilityStarsData = true
                    false
                } else {
                    true
                }
                visibilityStarsProgressBar = false
                isRefreshingStars = false
            },{
                if (isRefreshingStars) fragmentInterface.toastInternetConnection()
                visibilityStarsProgressBar = false
                isRefreshingStars = false
            })
        )
    }

    override fun onDestroy() {
        disposable.clear()
    }

    companion object {
        private var instance: StarsViewModel? = null
        var firstInstance = true

        fun getInstance(fragmentInterface: StarsInterface.View): StarsViewModel {
            if (instance == null) {
                instance = StarsViewModel(fragmentInterface)
                return instance as StarsViewModel
            }
            firstInstance = false
            return  instance as StarsViewModel
        }

        fun clearData() {
            instance = null
            firstInstance = true
            StarsRecyclerAdapter.clearData()
        }
    }
}