package ru.steeloscar.gitinfo.view.mainActivity.mainFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import ru.steeloscar.gitinfo.R
import ru.steeloscar.gitinfo.interfaces.fragmentsInterface.FollowersInterface
import ru.steeloscar.gitinfo.databinding.FragmentFollowersBinding
import ru.steeloscar.gitinfo.repository.api.model.UserProfile
import ru.steeloscar.gitinfo.view.mainActivity.adapters.FollowRecyclerAdapter
import ru.steeloscar.gitinfo.viewModel.mainFragmentsViewModel.FollowersViewModel

class FollowersFragment: Fragment(), FollowersInterface.View {

    private lateinit var binding: FragmentFollowersBinding
    private val followersViewModel = FollowersViewModel.getInstance(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentFollowersBinding>(inflater, R.layout.fragment_followers, container, false)

        if (FollowersViewModel.firstInstance) followersViewModel.getFollowers()
        with(binding) {
            viewModel = followersViewModel
            swipeRefreshFolllowers.setOnRefreshListener {
                followersViewModel.isRefreshingFollowers = true
                followersViewModel.getFollowers()
            }
            recyclerView.adapter = FollowRecyclerAdapter.getInstance(this)

            return root
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        followersViewModel.onDestroy()
    }

    override fun toastInternetConnection() {
        Toast.makeText(context, resources.getString(R.string.no_internet), Toast.LENGTH_SHORT).show()
    }

    override fun setRecyclerViewUserProfiles(userProfiles: ArrayList<UserProfile>) {
        (binding.recyclerView.adapter as FollowRecyclerAdapter).setUserProfiles(userProfiles)
    }
}