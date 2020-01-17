package ru.steeloscar.gitinfo.view.mainActivity.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import ru.steeloscar.gitinfo.R
import ru.steeloscar.gitinfo.databinding.RepositoryRecyclerViewLayoutBinding
import ru.steeloscar.gitinfo.repository.api.model.UserRepository


class RepositoriesRecyclerAdapter private constructor(val context: Context): RecyclerView.Adapter<RepositoriesRecyclerAdapter.ViewHolder>() {

    private var viewPool = RecyclerView.RecycledViewPool()

    private var userRepositories = ArrayList<UserRepository>()

    inner class ViewHolder(val binding: RepositoryRecyclerViewLayoutBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<RepositoryRecyclerViewLayoutBinding>(inflater, R.layout.repository_recycler_view_layout, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.binding) {
            userRepository = userRepositories[position]
            commitsRecyclerView.layoutManager = CommitsLayoutManager(context)
            commitsRecyclerView.adapter = CommitsRecyclerAdapter()
            commitsRecyclerView.setRecycledViewPool(viewPool)
            (commitsRecyclerView.adapter as CommitsRecyclerAdapter).setCommits(userRepositories[position].commits)
        }
    }

    override fun getItemCount() = userRepositories.size

    fun setUserRepositories(_userRepositories: ArrayList<UserRepository>) {
        userRepositories = _userRepositories
        notifyDataSetChanged()
    }

    companion object {
        private var instance: RepositoriesRecyclerAdapter? = null

        fun getInstance(context: Context): RepositoriesRecyclerAdapter {
            if (instance == null) instance = RepositoriesRecyclerAdapter(context)
            return  instance as RepositoriesRecyclerAdapter
        }

        fun clearData() {
            instance = null
        }
    }
}