package xyz.heydarrn.githubuserv2.model

import xyz.heydarrn.githubuserv2.network.ItemsItem

interface OnSelectedUser {
    fun selectThisUser(selectedUser:ItemsItem)
}