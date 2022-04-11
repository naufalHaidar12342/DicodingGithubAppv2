package xyz.heydarrn.githubuserv2.network

import com.google.gson.annotations.SerializedName

data class UserFollowingResponse(

	@field:SerializedName("avatar_url")
	val avatarUrl: String,

	@field:SerializedName("html_url")
	val htmlUrl: String,

	@field:SerializedName("login")
	val login: String
)
