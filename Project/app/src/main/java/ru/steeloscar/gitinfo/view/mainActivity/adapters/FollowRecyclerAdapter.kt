package ru.steeloscar.gitinfo.view.mainActivity.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import ru.steeloscar.gitinfo.R
import ru.steeloscar.gitinfo.databinding.FollowRecyclerAdapterLayoutBinding
import ru.steeloscar.gitinfo.databinding.FragmentFollowersBindingImpl
import ru.steeloscar.gitinfo.databinding.FragmentFollowingBindingImpl
import ru.steeloscar.gitinfo.repository.api.model.UserProfile
import ru.steeloscar.gitinfo.view.mainActivity.viewPagerFragments.FollowersFragment
import ru.steeloscar.gitinfo.view.mainActivity.viewPagerFragments.FollowingFragment

class FollowRecyclerAdapter private constructor(): RecyclerView.Adapter<FollowRecyclerAdapter.ViewHolder>() {

    private var userProfiles = ArrayList<UserProfile>()

    inner class ViewHolder(val binding: FollowRecyclerAdapterLayoutBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<FollowRecyclerAdapterLayoutBinding>(inflate, R.layout.follow_recycler_adapter_layout, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = userProfiles.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.userProfile = userProfiles[position]
    }

    fun setUserProfiles(_userProfiles: ArrayList<UserProfile>) {
        userProfiles = _userProfiles
        notifyDataSetChanged()
    }

    companion object {
        private var instanceFollowing: FollowRecyclerAdapter? = null
        private var instanceFollowers: FollowRecyclerAdapter? = null

        fun getInstance(type: Any): FollowRecyclerAdapter? =
            when(type) {
                is FollowersFragment, is FragmentFollowersBindingImpl -> {
                    if (instanceFollowers == null) instanceFollowers = FollowRecyclerAdapter()
                    instanceFollowers as FollowRecyclerAdapter
                }
                is FollowingFragment, is FragmentFollowingBindingImpl -> {
                    if (instanceFollowing == null) instanceFollowing = FollowRecyclerAdapter()
                    instanceFollowing as FollowRecyclerAdapter
                }
                else -> null
            }

        fun clearData() {
            instanceFollowing = null
            instanceFollowers = null
        }
    }
}