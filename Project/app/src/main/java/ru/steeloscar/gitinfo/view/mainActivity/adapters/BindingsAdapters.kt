package ru.steeloscar.gitinfo.view.mainActivity.adapters

import android.content.Intent
import android.net.Uri
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import ru.steeloscar.gitinfo.R

/*
Main Activity Binding Adapters
 */

@BindingAdapter("setupWithViewPager")
fun TabLayout.setViewPager(viewPager: ViewPager) {
    setupWithViewPager(viewPager)
}

/*
CommitRecyclerView Binding Adapters
 */

@BindingAdapter("commitsButtonText")
fun Button.commitMessage(buttonStatus: Boolean) {
    text = if (buttonStatus) resources.getString(R.string.hide_commits)
    else resources.getString(R.string.show_commits)
}

@BindingAdapter("commitSha")
fun TextView.commitSha(commitsSha: String) {
    text = commitsSha.substring(0..6)
}

@BindingAdapter("commitDate")
fun TextView.commitDate(commitDate: String) {
    val author = commitDate.substringBefore("//://")
    val date = commitDate.substringAfter("//://")
    val text = "$author committed ${date.substring(
        8..9
    )}.${date.substring(
        5..6
    )}.${date.substring(
        0..3
    )}"
    setText(text)
}


@BindingAdapter("loadCommitImage")
fun ImageView.setCommitImageUrl(url: String?) {
    if (!url.isNullOrEmpty()) {
        Glide.with(this.context)
            .load(url)
            .into(this)
    } else {
        Glide.with(this.context)
            .load(R.drawable.ic_commit)
            .into(this)
    }
}

/*
Overview Fragment Binding Adapters
 */

@BindingAdapter("loadImage")
fun setOverviewImageUrl(view: ImageView, url: String?) {
    if (!url.isNullOrEmpty()) {
        Glide.with(view.context)
            .load(url)
            .into(view)
    }
}

@BindingAdapter("textBlog")
fun formatBlogStyle(view: TextView, text: String?) {
    if (!text.isNullOrEmpty()) {
        view.text = text
        if (text.startsWith("https://") or text.startsWith("http://")) {
            view.setTextColor(ContextCompat.getColor(view.context, R.color.blue_web_reference))
            view.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(text))
                ContextCompat.startActivity(view.context, intent, null)
            }
        }
    }
}

@BindingAdapter("isRefreshingSwipeRefreshLayout")
fun SwipeRefreshLayout.isRefreshing(isLoading: Boolean) {
    setColorSchemeColors(ContextCompat.getColor(context, R.color.gray2))
    setProgressBackgroundColorSchemeResource(R.color.white)
    isRefreshing = isLoading
}

/*
Repositories Fragment Binding Adapters
 */

@BindingAdapter("date")
fun TextView.setDate(date: String) {
    if (!date.contains("null")) {
        var text = date.substringAfter(" ")
        text = "${date.substringBefore(" ")} ${text.substring(
            8..9
        )}.${text.substring(
            5..6
        )}.${text.substring(
            0..3
        )}"
        setText(text)
    }
}

@BindingAdapter("text")
fun TextView.setPrivateOrFork(statuses: String) {
    val fork = statuses.substringBefore(" ").toBoolean()
    val archived = statuses.substringAfter(" ").toBoolean()
    text = when {
        fork -> resources.getString(R.string.fork_repo)
        archived -> resources.getString(R.string.archived_repo)
        else -> resources.getString(R.string.private_repo)
    }
}