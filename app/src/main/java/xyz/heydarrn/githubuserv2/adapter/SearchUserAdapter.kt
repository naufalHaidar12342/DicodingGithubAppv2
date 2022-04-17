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
import xyz.heydarrn.githubuserv2.model.OnSelectedUser
import xyz.heydarrn.githubuserv2.model.SearchUserDiffCallback
import xyz.heydarrn.githubuserv2.network.ItemsItem

class SearchUserAdapter:RecyclerView.Adapter<SearchUserAdapter.SearchResultViewHolder>(),OnSelectedUser {
    private val list=ArrayList<ItemsItem>()
    private var choosenUser:OnSelectedUser?=null

    fun setChoosenUser(choosenUser: OnSelectedUser){
        this.choosenUser=choosenUser
    }

    fun setListForAdapter(shownList: List<ItemsItem>){
        val diffCallback=SearchUserDiffCallback(this.list,shownList)
        val diffResult=DiffUtil.calculateDiff(diffCallback)
        this.list.clear()
        this.list.addAll(shownList)
        diffResult.dispatchUpdatesTo(this)

    }
    inner class SearchResultViewHolder(private val bindUserCard: GithubUserCardBinding) :RecyclerView.ViewHolder(bindUserCard.root) {
        fun bindData(bindingItems: ItemsItem){
            bindUserCard.apply {
                Glide.with(itemView)
                    .load(bindingItems.avatarUrl)
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .circleCrop()
                    .into(githubProfilePicture)
                githubUsername.text=itemView.resources.getString(R.string.username_template, bindingItems.login)
                visitProfileButton.setOnClickListener {
                    val showInBrowser=Intent(Intent.ACTION_VIEW, Uri.parse(bindingItems.htmlUrl))
                    itemView.context.startActivity(showInBrowser)
                }
                root.setOnClickListener {
                    choosenUser?.selectThisUser(bindingItems)
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {
        val userView=GithubUserCardBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return SearchResultViewHolder(userView)
    }

    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
        holder.bindData(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun selectThisUser(selectedUser: ItemsItem) = Unit
}