package ru.steeloscar.gitinfo.view.mainActivity.mainFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import ru.steeloscar.gitinfo.R
import ru.steeloscar.gitinfo.interfaces.fragmentsInterface.FollowingInterface
import ru.steeloscar.gitinfo.databinding.FragmentFollowingBinding
import ru.steeloscar.gitinfo.repository.api.model.UserProfile
import ru.steeloscar.gitinfo.view.mainActivity.adapters.FollowRecyclerAdapter
import ru.steeloscar.gitinfo.viewModel.mainFragmentsViewModel.FollowingViewModel

class FollowingFragment: Fragment(), FollowingInterface.View {

    private lateinit var binding: FragmentFollowingBinding
    private val followingViewModel = FollowingViewModel.getInstance(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentFollowingBinding>(inflater, R.layout.fragment_following, container, false)

        if (FollowingViewModel.firstInstance) {
            followingViewModel.getFollowingUsers()
        }
        with(binding) {
            viewModel = followingViewModel
            swipeRefreshFollowing.setOnRefreshListener {
                followingViewModel.isRefreshingFollowing = true
                followingViewModel.getFollowingUsers()
            }
            recyclerView.adapter = FollowRecyclerAdapter.getInstance(this)

            return root
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        followingViewModel.onDestroy()
    }

    override fun toastInternetConnection() {
        Toast.makeText(context, resources.getString(R.string.no_internet), Toast.LENGTH_SHORT).show()
    }

    override fun setRecyclerViewFollowingUsers(followingUsers: ArrayList<UserProfile>) {
        (binding.recyclerView.adapter as FollowRecyclerAdapter).setUserProfiles(followingUsers)
    }
}
