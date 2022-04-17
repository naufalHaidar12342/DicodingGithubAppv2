package xyz.heydarrn.githubuserv2

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import xyz.heydarrn.githubuserv2.adapter.TabLayoutAdapter
import xyz.heydarrn.githubuserv2.databinding.FragmentUserDetailBinding
import xyz.heydarrn.githubuserv2.viewmodel.UserDetailViewModel

class UserDetailFragment : Fragment() {
    private var _bindingDetail:FragmentUserDetailBinding?=null
    private val bindingDetail get() = _bindingDetail
    private val detailViewModel by viewModels<UserDetailViewModel>()
    private val sendFollowerFollowing=Bundle()
    private var receiveUsername:String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _bindingDetail= FragmentUserDetailBinding.inflate(inflater,container,false)
        return bindingDetail?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //since this fragment only created when user click
        //the search result, we don't need to perform additional replace fragment
//        if (savedInstanceState!=null){
//            parentFragmentManager.commit {
//                replace<UserDetailFragment>(R.id.fragment_container)
//                addToBackStack(null)
//            }
//        }
        setTabLayout()
        showsUser()
    }

    private fun setTabLayout(){
        val tabSection=TabLayoutAdapter(this,sendFollowerFollowing)
        val viewpagers: ViewPager2? =bindingDetail?.viewpagersFollowerFollowing
        viewpagers?.adapter=tabSection

        val tabs:TabLayout?=bindingDetail?.tabLayoutFollowerFollowing
        TabLayoutMediator(tabs!!,viewpagers!!) { tab, tabPosition ->
            tab.text = resources.getString(TAB_NAMES[tabPosition])
        }.attach()
    }
    private fun showsUser(){
        //receive intent, sent from main activity

        receiveUsername=arguments?.getString(USERNAME_FROM_SEARCH)

        //with Bundle, sending string to fragment that associated with selected tab
        sendFollowerFollowing.putString(SEND_USERNAME,receiveUsername)

        //we got username, then pass it/feed it into setUserDetailedInfo()
        receiveUsername?.let { usernameChosen ->
            detailViewModel.setUserDetailedInfo(usernameChosen)
        }
        //observer for selectedUser data
        detailViewModel.monitorDetailUser().observe(viewLifecycleOwner) { observeUsername ->
            if (observeUsername != null) {
                bindingDetail?.apply {

                    Glide.with(requireActivity())
                        .load(observeUsername.avatarUrl)
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                        .circleCrop()
                        .into(imageViewProfileDetail)

                    if (observeUsername.name!=null){
                        textviewFullname.text=observeUsername.name
                    }else{
                        textviewFullname.text=resources.getString(R.string.fullname_got_null_response_template)
                    }

                    textviewUsername.text=resources.getString(R.string.username_template,observeUsername.login)

                    //bio
                    if (observeUsername.bio!=null){
                        textViewBio.text=observeUsername.bio
                    }else{
                        textViewBio.text=getString(R.string.null_bio_template)
                    }

                    // usually, user on github does not fill their location or company information,
                    // so, we need to check it
                    if (observeUsername.location!=null ){
                        textViewLocation.text=observeUsername.location
                    }else{
                        textViewLocation.text=getString(R.string.location_got_null_response_template)
                    }

                    if (observeUsername.company!=null){
                        textViewCompany.text=observeUsername.company
                    }else{
                        textViewCompany.text=getString(R.string.company_got_null_response_template)
                    }
                    // show how many PUBLIC repositories owned by selected user
                    textViewRepository.text=resources.getString(R.string.repository_string_template,observeUsername.publicRepos.toString())
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _bindingDetail=null
    }

    companion object {
        const val USERNAME_FROM_SEARCH="Selected user "
        private val TAB_NAMES= intArrayOf(
            R.string.followers_tab,
            R.string.following_tab
        )
        const val SEND_USERNAME="send username to be consumed by follower&following fragment"
    }
}