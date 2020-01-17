package ru.steeloscar.gitinfo.view.mainActivity.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import ru.steeloscar.gitinfo.R
import ru.steeloscar.gitinfo.databinding.StarsRecyclerViewLayoutBinding
import ru.steeloscar.gitinfo.repository.api.model.UserRepository

class StarsRecyclerAdapter private constructor(): RecyclerView.Adapter<StarsRecyclerAdapter.ViewHolder>() {

    private var userStars = ArrayList<UserRepository>()

    inner class ViewHolder(val binding: StarsRecyclerViewLayoutBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<StarsRecyclerViewLayoutBinding>(inflater, R.layout.stars_recycler_view_layout, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.userStars = userStars[position]
    }

    override fun getItemCount() = userStars.size

    fun setUserStars(_userStars: ArrayList<UserRepository>) {
        userStars = _userStars
        notifyDataSetChanged()
    }

    companion object {
        private var instance: StarsRecyclerAdapter? = null

        fun getInstance(): StarsRecyclerAdapter {
            if (instance == null) instance = StarsRecyclerAdapter()
            return instance as StarsRecyclerAdapter
        }

        fun clearData() {
            instance = null
        }
    }
}