package xyz.heydarrn.githubuserv2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils.replace
import androidx.activity.viewModels
import androidx.core.os.bundleOf
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.lifecycle.viewModelScope
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import xyz.heydarrn.githubuserv2.adapter.ActivityTabLayoutAdapter
import xyz.heydarrn.githubuserv2.adapter.TabLayoutAdapter
import xyz.heydarrn.githubuserv2.databinding.ActivityGithubUserDetailBinding
import xyz.heydarrn.githubuserv2.viewmodel.UserDetailViewModel

class GithubUserDetailActivity : AppCompatActivity() {
    private lateinit var bindingActivityDetail:ActivityGithubUserDetailBinding
    private val viewModelDetail by viewModels<UserDetailViewModel>()
    private var sendToFollowerAndFollowingFragment=Bundle()
    private var receiveUsername:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_github_user_detail)

        bindingActivityDetail= ActivityGithubUserDetailBinding.inflate(layoutInflater)
        setContentView(bindingActivityDetail.root)

        setOptionMenuForThisActivity()
        setTabLayout()
        showsUser()

    }

    private fun setOptionMenuForThisActivity(){
        bindingActivityDetail.toolbar.apply {
            inflateMenu(R.menu.option_menu)
            setOnMenuItemClickListener { itemFromMenu->
                when(itemFromMenu.itemId){
                    R.id.favourite_user_option ->{
                        supportFragmentManager.commit {
                            setReorderingAllowed(true)
                            replace<FavouriteUserFragment>(R.id.fragment_container)
                            addToBackStack(null)
                        }
                        true
                    }
                    R.id.theme_change_option->{
                        supportFragmentManager.commit {
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
    }

    private fun setTabLayout(){
        val tabSection= ActivityTabLayoutAdapter(this,sendToFollowerAndFollowingFragment)
        val viewpagers: ViewPager2 =bindingActivityDetail.viewpager2ActivityDetail
        viewpagers.adapter=tabSection

        val tabs: TabLayout=bindingActivityDetail.tabLayoutActivityDetail
        TabLayoutMediator(tabs,viewpagers){tab, position ->
            tab.text=resources.getString(TAB_NAMES[position])
        }.attach()
    }

    private fun showsUser(){
        //receive bundle, sent from Search User Fragment

        receiveUsername=intent.getStringExtra(SEND_USERNAME)

        //with Bundle, sending string to fragment that associated with selected tab
        sendToFollowerAndFollowingFragment.putString(FOLLOWER_FOLLOWING,receiveUsername)


        //we got username, then pass it/feed it into setUserDetailedInfo()
        receiveUsername?.let { usernameChosen ->
            viewModelDetail.setUserDetailedInfo(usernameChosen)
        }
        //observer for selectedUser data
        viewModelDetail.apply {
            viewModelScope.launch(Dispatchers.Main){
                viewModelDetail.monitorDetailUser().observe(this@GithubUserDetailActivity) { observeUsername ->
                    if (observeUsername != null) {
                        bindingActivityDetail.apply {
                            Glide.with(this@GithubUserDetailActivity)
                                .load(observeUsername.avatarUrl)
                                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                                .circleCrop()
                                .into(profilepicsActivityDetail)

                            if (observeUsername.name!=null){
                                fullnameActivityDetail.text=observeUsername.name
                            }else{
                                fullnameActivityDetail.text=resources.getString(R.string.fullname_got_null_response_template)
                            }

                            usernameActivityDetail.text=resources.getString(R.string.username_template,observeUsername.login)

                            // usually, user on github does not fill their location or company information,
                            // so, we need to check it
                            if (observeUsername.location!=null ){
                                locationActivityDetail.text=observeUsername.location
                            }else{
                                locationActivityDetail.text=getString(R.string.location_got_null_response_template)
                            }

                            if (observeUsername.company!=null){
                                companyActivityDetail.text=observeUsername.company
                            }else{
                                companyActivityDetail.text=getString(R.string.company_got_null_response_template)
                            }
                            // show how many PUBLIC repositories owned by selected user
                            repositoryActivityDetail.text=resources.getString(R.string.repository_string_template,observeUsername.publicRepos.toString())
                        }
                    }
                }
            }
        }
    }
    companion object{
        const val FOLLOWER_FOLLOWING="username for these fragment"
        const val SEND_USERNAME="username"
        private val TAB_NAMES= intArrayOf(
            R.string.followers_tab,
            R.string.following_tab
        )
    }
}