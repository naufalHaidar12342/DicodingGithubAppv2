package xyz.heydarrn.githubuserv2.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import xyz.heydarrn.githubuserv2.databinding.GithubUserCardBinding
import xyz.heydarrn.githubuserv2.model.SearchUserDiffCallback
import xyz.heydarrn.githubuserv2.network.ItemsItem

class SearchUserAdapter:RecyclerView.Adapter<SearchUserAdapter.SearchResultViewHolder>() {
    private val list=ArrayList<ItemsItem>()

    fun setListForAdapter(shownList: List<ItemsItem>){
        val diffCallback=SearchUserDiffCallback(this.list,shownList)
        val diffResult=DiffUtil.calculateDiff(diffCallback)
        this.list.clear()
        this.list.addAll(shownList)
        diffResult.dispatchUpdatesTo(this)

    }
    inner class SearchResultViewHolder(private val bindUserCard: GithubUserCardBinding) :RecyclerView.ViewHolder(bindUserCard.root) {
        fun bindData(itemsItem: ItemsItem){
            bindUserCard.apply {
                Glide.with(itemView)
                    .load(itemsItem.avatarUrl)
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .circleCrop()
                    .into(githubProfilePicture)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}