package xyz.heydarrn.githubuserv2.api

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import xyz.heydarrn.githubuserv2.BuildConfig

interface ApiService {

    @Headers(GITHUB_TOKEN)
    @GET("search/users")
    fun searchInsertedUsername(@Query("q") insertedUsername:String)

    companion object{
        const val GITHUB_TOKEN=BuildConfig.PERSONAL_ACCESS_TOKEN
    }
}