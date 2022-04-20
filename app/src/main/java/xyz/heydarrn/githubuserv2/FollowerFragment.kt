package xyz.heydarrn.githubuserv2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import xyz.heydarrn.githubuserv2.adapter.UserFollowerAdapter
import xyz.heydarrn.githubuserv2.databinding.FragmentFollowerBinding
import xyz.heydarrn.githubuserv2.model.LoadingAnimation
import xyz.heydarrn.githubuserv2.viewmodel.FollowerViewModel

class FollowerFragment : Fragment(), LoadingAnimation {
    private var _bindingFollower: FragmentFollowerBinding?=null
    private val bindingFollower get() = _bindingFollower
    private val followerViewModel by viewModels<FollowerViewModel>()
    private val followerAdapter by lazy { UserFollowerAdapter() }
    private lateinit var followerName:String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _bindingFollower= FragmentFollowerBinding.inflate(inflater,container,false)
        return bindingFollower?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setFollowerRecyclerView()
        setFollowerOfThisUser()
        observeFollowerData()
    }

    private fun setFollowerOfThisUser(){
        val argumentReceived=arguments
        followerName=argumentReceived?.getString(DetailOfSelectedUserFragment.SEND_USERNAME).toString()
        followerViewModel.setUserFollowersInfo(followerName)
    }

    private fun observeFollowerData(){
        followerViewModel.viewModelScope.launch {
            followerViewModel.setSelectedUserFollowersInfo().observe(viewLifecycleOwner){ monitorThisList ->
                if (monitorThisList!=null){
                    showLoadingProgress(false)
                    followerAdapter.setListOfFollower(monitorThisList)
                }
            }
        }
    }

    private fun setFollowerRecyclerView(){
        bindingFollower?.recyclerViewFollower?.apply {
            this.setHasFixedSize(true)
            this.layoutManager=LinearLayoutManager(context)
            this.adapter=followerAdapter
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _bindingFollower=null
    }

    override fun showLoadingProgress(loadingState: Boolean) {
        when(loadingState){
            true -> bindingFollower?.progressBarFollower?.visibility=View.VISIBLE
            false -> bindingFollower?.progressBarFollower?.visibility=View.GONE
        }
    }
}