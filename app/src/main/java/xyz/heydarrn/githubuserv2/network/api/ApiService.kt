package xyz.heydarrn.githubuserv2.network.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query
import xyz.heydarrn.githubuserv2.BuildConfig
import xyz.heydarrn.githubuserv2.network.*

interface ApiService {
    @Headers(GITHUB_TOKEN)
    @GET("search/users")
    fun searchInsertedUsername(@Query("q") insertedUsername:String) : Call<SearchResultResponse>

    @Headers(GITHUB_TOKEN)
    @GET("users/{username}")
    fun getSelectedUserInfo(@Path("username") showUsernameInfo:String) :Call<DetailedUserInfoResponse>

    @Headers(GITHUB_TOKEN)
    @GET("users/{username}/followers")
    fun selectedUserFollower(@Path("username") showThisUserFollower:String):Call<ArrayList<UserFollowerResponse>>

    @Headers(GITHUB_TOKEN)
    @GET("users/{username}/following")
    fun selectedUserFollowing(@Path("username") showThisUserFollowing: String) : Call<ArrayList<UserFollowingResponse>>

    companion object{
        const val GITHUB_TOKEN=BuildConfig.PERSONAL_ACCESS_TOKEN
    }
}