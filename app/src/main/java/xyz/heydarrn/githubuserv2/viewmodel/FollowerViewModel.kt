package xyz.heydarrn.githubuserv2.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import xyz.heydarrn.githubuserv2.network.UserFollowerResponse
import xyz.heydarrn.githubuserv2.network.api.ApiConfig

class FollowerViewModel : ViewModel() {
    private var _showFollowerOfUser= MutableLiveData<ArrayList<UserFollowerResponse>>()
    private val showFollowerOfUser: LiveData<ArrayList<UserFollowerResponse>> = _showFollowerOfUser

    fun setUserFollowersInfo(selectedUser:String){
        val followerClient= ApiConfig.getApiService().selectedUserFollower(selectedUser)
        followerClient.enqueue(object : Callback<ArrayList<UserFollowerResponse>> {
            override fun onResponse(
                call: Call<ArrayList<UserFollowerResponse>>,
                response: Response<ArrayList<UserFollowerResponse>>
            ) {
                if (response.isSuccessful) {
                    _showFollowerOfUser.postValue(response.body())
                }
                Log.d("follower success", "onResponse: ${response.message()}")
            }

            override fun onFailure(
                call: Call<ArrayList<UserFollowerResponse>>,
                t: Throwable
            ) {
                Log.d("follower fail", "onFailure: ${t.message}")
            }

        })
    }

    fun setSelectedUserFollowersInfo(): LiveData<ArrayList<UserFollowerResponse>> = showFollowerOfUser

}