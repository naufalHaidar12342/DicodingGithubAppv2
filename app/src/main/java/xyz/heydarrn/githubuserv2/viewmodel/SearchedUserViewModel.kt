package xyz.heydarrn.githubuserv2.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import xyz.heydarrn.githubuserv2.network.ItemsItem
import xyz.heydarrn.githubuserv2.network.SearchResultResponse
import xyz.heydarrn.githubuserv2.network.api.ApiConfig

class SearchedUserViewModel : ViewModel() {
    private var _listOfSearchedUser= MutableLiveData<ArrayList<ItemsItem>>()
    private val listOfSearchedUser: LiveData<ArrayList<ItemsItem>> = _listOfSearchedUser

    fun searchUserOnSubmittedText(findUser:String){
        val client= ApiConfig.getApiService().searchInsertedUsername(findUser)
        client.enqueue(object : Callback<SearchResultResponse> {

            override fun onResponse(
                call: Call<SearchResultResponse>,
                response: Response<SearchResultResponse>
            ) {
                if (response.isSuccessful){
                    /*if successfully got response, insert the response body to arrayList "items"*/
                    _listOfSearchedUser.value= response.body()?.items
                }else{
                    Log.d("success get response", "onResponse: ${response.message()}")
                }

            }

            override fun onFailure(call: Call<SearchResultResponse>, t: Throwable) {
                Log.d("fail to get response", "onFailure: ${t.message}")
            }

        })
    }

    fun setResultForAdapter(): LiveData<ArrayList<ItemsItem>> = listOfSearchedUser
}