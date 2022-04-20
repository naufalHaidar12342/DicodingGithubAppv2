package xyz.heydarrn.githubuserv2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import xyz.heydarrn.githubuserv2.databinding.FragmentFollowingBinding
import xyz.heydarrn.githubuserv2.model.LoadingAnimation

class FollowingFragment : Fragment(),LoadingAnimation {
    private var _bindFollowing:FragmentFollowingBinding?=null
    private val bindFollowing get() = _bindFollowing


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_following, container, false)
    }

    override fun showLoadingProgress(loadingState: Boolean) {
        when(loadingState){
            true ->""
        }
    }


}