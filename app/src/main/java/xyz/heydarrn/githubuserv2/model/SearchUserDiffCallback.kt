package xyz.heydarrn.githubuserv2.model

import androidx.recyclerview.widget.DiffUtil
import xyz.heydarrn.githubuserv2.network.ItemsItem

class SearchUserDiffCallback (
    private val oldSearchResult:List<ItemsItem>,
    private val newSearchResult:List<ItemsItem>
    ) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldSearchResult.size
    }

    override fun getNewListSize(): Int {
        return newSearchResult.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldSearchResult[oldItemPosition].login == newSearchResult[newItemPosition].login
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldSearch=oldSearchResult[oldItemPosition]
        val newSearch=newSearchResult[newItemPosition]
        return oldSearch.login == newSearch.login &&
                oldSearch.avatarUrl == newSearch.avatarUrl &&
                oldSearch.htmlUrl == newSearch.htmlUrl
    }
}