package ru.steeloscar.gitinfo.viewModel

import ru.steeloscar.gitinfo.interfaces.MainActivityInterface
import ru.steeloscar.gitinfo.repository.MainRepository
import ru.steeloscar.gitinfo.viewModel.mainFragmentsViewModel.*

class MainViewModel(activityInterface: MainActivityInterface.View): MainActivityInterface.ViewModel {

    private val repository = MainRepository.newInstance(activityInterface.getSharedPreferences())

    override fun signOut() {
        repository.setTokenSharedPreferences("")
        OverviewViewModel.clearData()
        RepositoriesViewModel.clearData()
        StarsViewModel.clearData()
        FollowersViewModel.clearData()
        FollowingViewModel.clearData()
    }
}