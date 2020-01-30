package ru.steeloscar.gitinfo.view.mainActivity.viewPagerFragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import ru.steeloscar.gitinfo.R
import ru.steeloscar.gitinfo.databinding.FragmentOverviewBinding
import ru.steeloscar.gitinfo.interfaces.MainActivityViewInterface
import ru.steeloscar.gitinfo.repository.api.model.UserProfile
import ru.steeloscar.gitinfo.viewModel.viewPagerFragmentsViewModel.OverviewViewModel

class OverviewFragment: Fragment(), MainActivityViewInterface.Overview {

    private val overviewViewModel = OverviewViewModel.newInstance(this)
    private lateinit var binding: FragmentOverviewBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentOverviewBinding>(inflater, R.layout.fragment_overview, container, false)

        overviewViewModel.getUserProfile()
        with(binding) {
            if (UserProfile.overviewUserProfile != null) userProfile = UserProfile.overviewUserProfile
            viewModel = overviewViewModel
            swipeRefreshOverview.setOnRefreshListener {
                overviewViewModel.isRefreshing.set(true)
                overviewViewModel.getUserProfile()
            }
            return root
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        overviewViewModel.onDestroy()
    }

    override fun toastInternetConnection() {
        Toast.makeText(context, resources.getString(R.string.no_internet), Toast.LENGTH_SHORT).show()
    }

    override fun setUserProfile(userProfile: UserProfile) {
        binding.userProfile = userProfile
    }

    override fun startIntent(intent: Intent) {
        startActivity(intent)
    }

    companion object {
        private var instance: OverviewFragment? = null

        fun getInstance(): OverviewFragment{
            if (instance == null) instance = OverviewFragment()
            return instance as OverviewFragment
        }
    }
}