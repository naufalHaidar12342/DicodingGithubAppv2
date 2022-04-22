package xyz.heydarrn.githubuserv2

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.Dispatchers
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
    private var followerName:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener("request_username"){ _, bundle ->
            val argumentReceived=arguments
            Log.d("oncreate argument", "onCreate: $argumentReceived")
            followerName=bundle.getString(DetailOfSelectedUserFragment.USERNAME_FROM_SEARCH)
            Log.d("receive username", "oncreate: $followerName")
            followerName?.let { followerViewModel.setUserFollowersInfo(it) }

        }
    }

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
        observeFollowerData()
//        setFollowerOfThisUser()
    }

    private fun setFollowerOfThisUser(){
        val argumentReceived=arguments
//        followerName=argumentReceived?.getString(DetailOfSelectedUserFragment.USERNAME_FROM_SEARCH).toString()

//        followerName?.let { followerViewModel.setUserFollowersInfo(it) }


    }

    private fun observeFollowerData(){
        followerViewModel.viewModelScope.launch(Dispatchers.Main) {
            followerViewModel.setSelectedUserFollowersInfo().observe(viewLifecycleOwner){ monitorThisList ->
                if (monitorThisList!=null){
                    followerAdapter.setListOfFollower(monitorThisList)
//                    showLoadingProgress(false)
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