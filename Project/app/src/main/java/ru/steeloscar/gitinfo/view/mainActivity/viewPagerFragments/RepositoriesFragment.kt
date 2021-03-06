package ru.steeloscar.gitinfo.view.mainActivity.viewPagerFragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import ru.steeloscar.gitinfo.R
import ru.steeloscar.gitinfo.databinding.FragmentRepositoriesBinding
import ru.steeloscar.gitinfo.interfaces.MainActivityViewInterface
import ru.steeloscar.gitinfo.repository.api.model.UserRepository
import ru.steeloscar.gitinfo.view.mainActivity.adapters.RepositoriesRecyclerAdapter
import ru.steeloscar.gitinfo.viewModel.viewPagerFragmentsViewModel.RepositoriesViewModel

class RepositoriesFragment: Fragment(), MainActivityViewInterface.Repositories{

    private lateinit var binding: FragmentRepositoriesBinding
    private var repositoriesViewModel: RepositoriesViewModel = RepositoriesViewModel.getInstance(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentRepositoriesBinding>(inflater, R.layout.fragment_repositories, container, false)

        if (RepositoriesViewModel.firstInstance) repositoriesViewModel.getRepositories()
        with(binding) {
            viewModel = repositoriesViewModel
            swipeRefreshRepositories.setOnRefreshListener {
                repositoriesViewModel.isRefreshingRepositories = true
                repositoriesViewModel.getRepositories()
            }
            recyclerView.adapter = RepositoriesRecyclerAdapter.getInstance(context as Context)
            return root
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        repositoriesViewModel.onDestroy()
    }

    override fun toastInternetConnection() {
        Toast.makeText(context, resources.getString(R.string.no_internet), Toast.LENGTH_SHORT).show()
    }

    override fun setUserRepositories(userRepositories: ArrayList<UserRepository>) {
        (binding.recyclerView.adapter as RepositoriesRecyclerAdapter)
            .setUserRepositories(userRepositories)
    }

    companion object {
        private var instance: RepositoriesFragment? = null

        fun getInstance(): RepositoriesFragment{
            if (instance == null) instance = RepositoriesFragment()
            return instance as RepositoriesFragment
        }
    }
}