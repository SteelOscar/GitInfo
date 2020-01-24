package ru.steeloscar.gitinfo.view.mainActivity.adapters

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import ru.steeloscar.gitinfo.view.mainActivity.viewPagerFragments.*

class MainViewPagerAdapter(fm: FragmentManager, behavior: Int) : FragmentPagerAdapter(fm, behavior) {

    private val fragmentList = arrayOf(
        OverviewFragment(),
        RepositoriesFragment(),
        StarsFragment(),
        FollowersFragment(),
        FollowingFragment()
    )
    private val titleFragmentList = arrayOf(
        "Overview",
        "Repositories",
        "Stars",
        "Followers",
        "Following"
    )

    override fun getItem(position: Int): Fragment = fragmentList[position]

    override fun getCount(): Int = fragmentList.size

    override fun getPageTitle(position: Int): CharSequence? = titleFragmentList[position]

//    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
//        //Do nothing so that ViewPager don't destroy fragments instance
//    }
}