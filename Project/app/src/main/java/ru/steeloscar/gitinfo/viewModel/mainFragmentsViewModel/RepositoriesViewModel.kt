package ru.steeloscar.gitinfo.viewModel.mainFragmentsViewModel

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import ru.steeloscar.gitinfo.interfaces.fragmentsInterface.RepositoriesInterface
import ru.steeloscar.gitinfo.repository.MainRepository
import ru.steeloscar.gitinfo.repository.api.model.UserRepository
import ru.steeloscar.gitinfo.view.mainActivity.adapters.RepositoriesRecyclerAdapter

class RepositoriesViewModel private constructor(private val fragmentInterface: RepositoriesInterface.View): RepositoriesInterface.ViewModel, BaseObservable() {

    private var count = 0

    var visibilityRepositoriesData = false
        @Bindable get
        private set(value) {
            field = value
            notifyPropertyChanged(BR.visibilityRepositoriesData)
        }

    var visibilityRepositoriesProgressBar: Boolean = true
        @Bindable get
        private set(value) {
            field = value
            notifyPropertyChanged(BR.visibilityRepositoriesProgressBar)
        }

    var isRefreshingRepositories: Boolean = false
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.refreshingRepositories)
        }

    var visibilityEmptyFormRepositories: Boolean = false
        @Bindable get
        private set(value) {
            field = value
            notifyPropertyChanged(BR.visibilityEmptyFormRepositories)
        }

    private val repository = MainRepository.getInstance()
    private val disposable = CompositeDisposable()

    override fun getRepositories(){
        disposable.add(repository.gerRepositories()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                visibilityEmptyFormRepositories = if (it.isNotEmpty()){
                    it.map { userRepository ->
                        userRepository.visibilityProgressCommits = true
                        userRepository.commitsEmpty = true
                    }
//                    fragmentInterface.setUserRepositories(it)
                    getCommits(it)
                    false
                } else {
                    visibilityRepositoriesData = false
                    visibilityRepositoriesProgressBar = false
                    isRefreshingRepositories = false
                    true
                }
            },{
                if (isRefreshingRepositories) fragmentInterface.toastInternetConnection()
                visibilityRepositoriesData = false
                visibilityRepositoriesProgressBar = false
                isRefreshingRepositories = false
            })
        )
    }



    private fun getCommits(arrayListRepositories: ArrayList<UserRepository>) {
        arrayListRepositories.map { userRepository ->
            disposable.add(repository.getRepositoryCommits(userRepository.owner.login.toString(), userRepository.name.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({
                    userRepository.commits = it
                    userRepository.commitsEmpty = false
                    userRepository.visibilityProgressCommits = false
                    count++
                    if (count == arrayListRepositories.size) {
                        fragmentInterface.setUserRepositories(arrayListRepositories)
                        visibilityRepositoriesProgressBar = false
                        visibilityRepositoriesData = true
                        isRefreshingRepositories = false
                        count = 0
                    }
                },{
                    userRepository.commitsEmpty = true
                    userRepository.visibilityProgressCommits = false
                    count++
                    if (count == arrayListRepositories.size) {
                        fragmentInterface.setUserRepositories(arrayListRepositories)
                        visibilityRepositoriesProgressBar = false
                        visibilityRepositoriesData = true
                        isRefreshingRepositories = false
                        count = 0
                    }
                })
            )
        }
    }

    override fun onDestroy() {
        disposable.clear()
    }

    companion object {
        private var instance: RepositoriesViewModel? = null
        var firstInstance = true

        fun getInstance(fragmentInterface: RepositoriesInterface.View): RepositoriesViewModel =
            if (instance == null) {
                instance = RepositoriesViewModel(fragmentInterface)
                instance as RepositoriesViewModel
            } else {
                firstInstance = false
                instance as RepositoriesViewModel
            }

        fun clearData() {
            instance = null
            firstInstance = true
            RepositoriesRecyclerAdapter.clearData()
        }
    }
}