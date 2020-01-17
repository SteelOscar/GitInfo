package ru.steeloscar.gitinfo.view.mainActivity.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import ru.steeloscar.gitinfo.R
import ru.steeloscar.gitinfo.databinding.CommitsRecyclerViewLayoutBinding
import ru.steeloscar.gitinfo.repository.api.model.RepositoryCommit

class CommitsRecyclerAdapter: RecyclerView.Adapter<CommitsRecyclerAdapter.CommitsViewHolder>() {

    private var repositoriesCommits = ArrayList<RepositoryCommit>()

    inner class CommitsViewHolder(val binding: CommitsRecyclerViewLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommitsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<CommitsRecyclerViewLayoutBinding>(
            inflater,
            R.layout.commits_recycler_view_layout,
            parent,
            false
        )
        return CommitsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommitsViewHolder, position: Int) {
        holder.binding.repositoryCommit = repositoriesCommits[position]
    }

    override fun getItemCount(): Int = repositoriesCommits.size

    fun setCommits(commits: ArrayList<RepositoryCommit>?) {
        if (commits != null) {
            repositoriesCommits = commits
            notifyDataSetChanged()
        }
    }
}