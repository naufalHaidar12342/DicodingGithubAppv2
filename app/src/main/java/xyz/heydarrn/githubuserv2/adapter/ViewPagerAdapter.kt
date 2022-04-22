package xyz.heydarrn.githubuserv2.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import xyz.heydarrn.githubuserv2.FollowerFragment
import xyz.heydarrn.githubuserv2.FollowingFragment

class ViewPagerAdapter(fa: FragmentActivity?) : FragmentStateAdapter(fa!!) {
    private val mFragments = arrayOf<Fragment>( //Initialize fragments views
        //Fragment views are initialized like any other fragment (Extending Fragment)
        FollowerFragment(),  //First fragment to be displayed within the pager tab number 1
        FollowingFragment()
    )
    val mFragmentNames = arrayOf( //Tabs names array
        "First Tab",
        "SecondTab"
    )

    override fun getItemCount(): Int {
        return mFragments.size //Number of fragments displayed
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    override fun createFragment(position: Int): Fragment {
        return mFragments[position]
    }
}