package xyz.heydarrn.githubuserv2.network

import com.google.gson.annotations.SerializedName

data class DetailedUserInfoResponse(

	@field:SerializedName("avatar_url")
	val avatarUrl: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("bio")
	val bio: String? = null,

	@field:SerializedName("company")
	val company: Any? = null,

	@field:SerializedName("location")
	val location: String? = null,

	@field:SerializedName("public_repos")
	val publicRepos: Int? = null,

	@field:SerializedName("login")
	val login: String? = null
)