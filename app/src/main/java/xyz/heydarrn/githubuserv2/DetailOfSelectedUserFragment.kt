package xyz.heydarrn.githubuserv2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import xyz.heydarrn.githubuserv2.adapter.TabLayoutAdapter
import xyz.heydarrn.githubuserv2.databinding.FragmentDetailOfSelectedUserBinding
import xyz.heydarrn.githubuserv2.viewmodel.UserDetailViewModel

class DetailOfSelectedUserFragment : Fragment() {
    private var _bindingDetailUser:FragmentDetailOfSelectedUserBinding? = null
    private val bindingDetailUser get() = _bindingDetailUser
    private val detailViewModel by viewModels<UserDetailViewModel>()
    private val sendToFollowerAndFollowingFragment by lazy { Bundle() }
    private var receiveUsername:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _bindingDetailUser= FragmentDetailOfSelectedUserBinding.inflate(inflater,container,false)
        return bindingDetailUser?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindingDetailUser?.toolbar?.apply {
            inflateMenu(R.menu.option_menu)
            setOnMenuItemClickListener { itemFromMenu->
                when(itemFromMenu.itemId){
                    R.id.favourite_user_option ->{
                        parentFragmentManager.commit {
                            setReorderingAllowed(true)
                            replace<FavouriteUserFragment>(R.id.fragment_container)
                            addToBackStack(null)
                        }
                        true
                    }
                    R.id.theme_change_option->{
                        parentFragmentManager.commit {
                            setReorderingAllowed(true)
                            replace<ThemeSettingFragment>(R.id.fragment_container)
                            addToBackStack(null)
                        }
                        true
                    }
                    else ->false
                }
            }
        }
        setTabLayout()
        showsUser()
    }

    private fun setTabLayout(){
        val tabSection= TabLayoutAdapter(this,sendToFollowerAndFollowingFragment)
        val viewpagers: ViewPager2? =bindingDetailUser?.viewPager2DetailOfUser
        viewpagers?.adapter=tabSection

        val tabs: TabLayout?=bindingDetailUser?.tabLayoutDetailOfUser
        TabLayoutMediator(tabs!!,viewpagers!!) { tab, tabPosition ->
            tab.text = resources.getString(TAB_NAMES[tabPosition])
        }.attach()
    }
    private fun showsUser(){
        //receive bundle, sent from Search User Fragment

        receiveUsername=arguments?.getString(USERNAME_FROM_SEARCH)

        //with Bundle, sending string to fragment that associated with selected tab
        sendToFollowerAndFollowingFragment.putString(SEND_USERNAME,receiveUsername)

        //we got username, then pass it/feed it into setUserDetailedInfo()
        receiveUsername?.let { usernameChosen ->
            detailViewModel.setUserDetailedInfo(usernameChosen)
        }
        //observer for selectedUser data
        detailViewModel.apply {
            viewModelScope.launch(Dispatchers.Main){
                detailViewModel.monitorDetailUser().observe(viewLifecycleOwner) { observeUsername ->
                    if (observeUsername != null) {
                        bindingDetailUser?.apply {
                            Glide.with(requireActivity())
                                .load(observeUsername.avatarUrl)
                                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                                .circleCrop()
                                .into(profilePictureDetailOfUser)

                            if (observeUsername.name!=null){
                                fullnameDetailOfUser.text=observeUsername.name
                            }else{
                                fullnameDetailOfUser.text=resources.getString(R.string.fullname_got_null_response_template)
                            }

                            usernameDetailOfUser.text=resources.getString(R.string.username_template,observeUsername.login)

                            // usually, user on github does not fill their location or company information,
                            // so, we need to check it
                            if (observeUsername.location!=null ){
                                locationDetailOfUser.text=observeUsername.location
                            }else{
                                locationDetailOfUser.text=getString(R.string.location_got_null_response_template)
                            }

                            if (observeUsername.company!=null){
                                companyDetailOfUser.text=observeUsername.company
                            }else{
                                companyDetailOfUser.text=getString(R.string.company_got_null_response_template)
                            }
                            // show how many PUBLIC repositories owned by selected user
                            repositoryDetailOfUser.text=resources.getString(R.string.repository_string_template,observeUsername.publicRepos.toString())
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _bindingDetailUser=null
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