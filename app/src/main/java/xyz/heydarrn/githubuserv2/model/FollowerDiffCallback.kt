package xyz.heydarrn.githubuserv2.model

import androidx.recyclerview.widget.DiffUtil
import xyz.heydarrn.githubuserv2.network.UserFollowerResponse

class FollowerDiffCallback(
    private val oldFollowerList:List<UserFollowerResponse>,
    private val newFollowerList:List<UserFollowerResponse>
    ) :DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldFollowerList.size
    }

    override fun getNewListSize(): Int {
        return newFollowerList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldFollowerList[oldItemPosition].login == newFollowerList[newItemPosition].login
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val newFollower=newFollowerList[newItemPosition]
        val oldFollower=oldFollowerList[oldItemPosition]
        return oldFollower.login == newFollower.login &&
                oldFollower.avatarUrl == newFollower.avatarUrl &&
                oldFollower.htmlUrl == newFollower.htmlUrl
    }

}