package xyz.heydarrn.githubuserv2.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import xyz.heydarrn.githubuserv2.network.DetailedUserInfoResponse
import xyz.heydarrn.githubuserv2.network.api.ApiConfig

class UserDetailViewModel:ViewModel() {
    private var _showUserDetail= MutableLiveData<DetailedUserInfoResponse>()
    private val openDetailedInfo: LiveData<DetailedUserInfoResponse> = _showUserDetail

    fun setUserDetailedInfo(selectedUsername:String){
        val detailClient= ApiConfig.getApiService().getSelectedUserInfo(selectedUsername)
        detailClient.enqueue(object : Callback<DetailedUserInfoResponse> {
            override fun onResponse(
                call: Call<DetailedUserInfoResponse>,
                response: Response<DetailedUserInfoResponse>
            ) {
                if (response.isSuccessful) {
                    _showUserDetail.value=response.body()
                }
                Log.d("detail success", "onResponse: "+ response.message())
            }

            override fun onFailure(call: Call<DetailedUserInfoResponse>, t: Throwable) {
                Log.d("detail fail", "onFailure: "+t.message)
            }

        })
    }

    fun monitorDetailUser():LiveData<DetailedUserInfoResponse> = openDetailedInfo
}