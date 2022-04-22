package xyz.heydarrn.githubuserv2.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import xyz.heydarrn.githubuserv2.FollowerFragment
import xyz.heydarrn.githubuserv2.FollowingFragment

class TabLayoutAdapter(fragment:FragmentActivity, data:Bundle):FragmentStateAdapter(fragment) {
    //initialize fragmentBundle value from parameter
    private var fragmentBundle:Bundle = data

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment:Fragment?=null
        when(position){
            0 -> {
                fragment = FollowerFragment()
                fragment.arguments =this.fragmentBundle
            }
            1 -> {
                fragment = FollowingFragment()
                fragment.arguments=this.fragmentBundle
            }
        }
        /*when one of tab selected, it will send argument, containing the bundle here,
        * to the fragment associated with the selected tabs*/
        fragment?.arguments=this.fragmentBundle
        return fragment as Fragment
    }
}