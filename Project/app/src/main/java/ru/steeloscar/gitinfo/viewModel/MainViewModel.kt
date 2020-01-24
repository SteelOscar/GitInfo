package ru.steeloscar.gitinfo.viewModel

import ru.steeloscar.gitinfo.repository.GitInfoPreferences
import ru.steeloscar.gitinfo.repository.Repository
import ru.steeloscar.gitinfo.viewModel.viewPagerFragmentsViewModel.*

class MainViewModel{
    private val repository = Repository.getInstance()

    fun signOut() {
        GitInfoPreferences.setToken(null)
        repository.setTokenSharedPreferences("")
        OverviewViewModel.clearData()
        RepositoriesViewModel.clearData()
        StarsViewModel.clearData()
        FollowersViewModel.clearData()
        FollowingViewModel.clearData()
    }
}