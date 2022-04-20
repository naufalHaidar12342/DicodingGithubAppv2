package xyz.heydarrn.githubuserv2.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import xyz.heydarrn.githubuserv2.R
import xyz.heydarrn.githubuserv2.databinding.GithubUserCardBinding
import xyz.heydarrn.githubuserv2.model.FollowerDiffCallback
import xyz.heydarrn.githubuserv2.model.OnSelectedUser
import xyz.heydarrn.githubuserv2.model.SearchUserDiffCallback
import xyz.heydarrn.githubuserv2.network.ItemsItem
import xyz.heydarrn.githubuserv2.network.UserFollowerResponse

class UserFollowerAdapter : RecyclerView.Adapter<UserFollowerAdapter.FollowerViewHolder>(){
    private val list=ArrayList<UserFollowerResponse>()

    fun setListOfFollower(followersList:List<UserFollowerResponse>){
        val diffCallback= FollowerDiffCallback(this.list,followersList)
        val diffResult= DiffUtil.calculateDiff(diffCallback)
        this.list.clear()
        this.list.addAll(followersList)
        diffResult.dispatchUpdatesTo(this)
    }
    inner class FollowerViewHolder(private val userCardBinding: GithubUserCardBinding):RecyclerView.ViewHolder(userCardBinding.root) {
        fun bindFollowerData(bindFollower: UserFollowerResponse) {
            userCardBinding.apply {
                Glide.with(itemView)
                    .load(bindFollower.avatarUrl)
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .circleCrop()
                    .into(githubProfilePicture)
                githubUsername.text =
                    itemView.resources.getString(R.string.username_template, bindFollower.login)
                visitProfileButton.setOnClickListener {
                    val showInBrowser = Intent(Intent.ACTION_VIEW, Uri.parse(bindFollower.htmlUrl))
                    itemView.context.startActivity(showInBrowser)
                }
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowerViewHolder {
        val userView=GithubUserCardBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return FollowerViewHolder(userView)
    }

    override fun onBindViewHolder(holder: FollowerViewHolder, position: Int) {
        holder.bindFollowerData(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }


}