package ru.steeloscar.gitinfo.view.mainActivity.mainFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import ru.steeloscar.gitinfo.R
import ru.steeloscar.gitinfo.interfaces.fragmentsInterface.StarsInterface
import ru.steeloscar.gitinfo.databinding.FragmentStarsBinding
import ru.steeloscar.gitinfo.repository.api.model.UserRepository
import ru.steeloscar.gitinfo.view.mainActivity.adapters.StarsRecyclerAdapter
import ru.steeloscar.gitinfo.viewModel.mainFragmentsViewModel.StarsViewModel

class StarsFragment: Fragment(), StarsInterface.View {

    private lateinit var binding: FragmentStarsBinding
    private val starsViewModel = StarsViewModel.getInstance(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentStarsBinding>(inflater, R.layout.fragment_stars, container, false)

        if (StarsViewModel.firstInstance) starsViewModel.getStars()

        with(binding) {
            viewModel = starsViewModel
            swipeRefreshStars.setOnRefreshListener {
                starsViewModel.isRefreshingStars = true
                starsViewModel.getStars()
            }
            recyclerView.adapter = StarsRecyclerAdapter.getInstance()
        }
        
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        starsViewModel.onDestroy()
    }
    
    override fun toastInternetConnection() {
        Toast.makeText(context, resources.getString(R.string.no_internet), Toast.LENGTH_SHORT).show()
    }

    override fun setUserStars(userStars: ArrayList<UserRepository>) {
        (binding.recyclerView.adapter as StarsRecyclerAdapter).setUserStars(userStars)
    }
}