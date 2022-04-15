package xyz.heydarrn.githubuserv2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import xyz.heydarrn.githubuserv2.adapter.SearchUserAdapter
import xyz.heydarrn.githubuserv2.databinding.FragmentSearchUserBinding
import xyz.heydarrn.githubuserv2.model.LoadingAnimation
import xyz.heydarrn.githubuserv2.viewmodel.SearchedUserViewModel


class SearchUserFragment : Fragment(),LoadingAnimation {
    private var _bindingSearchUser:FragmentSearchUserBinding? = null
    private val bindingSearchUser get() = _bindingSearchUser
    private val userViewModel by viewModels<SearchedUserViewModel>()
    private val searchedUserAdapter by lazy { SearchUserAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _bindingSearchUser= FragmentSearchUserBinding.inflate(inflater,container,false)
        return bindingSearchUser?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //hide progress bar when app launched.
        // just hiding it, not make it disappear/gone (gone means this object will be set to 0,0)
        bindingSearchUser?.searchProgressBar?.visibility=View.INVISIBLE

        getInsertedUsername()
        setAdapter()
        observeViewModel()
    }

    private fun setAdapter(){
        bindingSearchUser?.recyclerViewSearch?.apply {
            this.setHasFixedSize(true)
            this.layoutManager= LinearLayoutManager(context)
            this.adapter=searchedUserAdapter
        }
    }
    private fun observeViewModel(){
        userViewModel.apply {
            viewModelScope.launch(Dispatchers.Main){
                setResultForAdapter().observe(viewLifecycleOwner){ newList->
                    if (newList!=null){
                        searchedUserAdapter.setListForAdapter(newList)
                        showLoadingProgress(false)
                    }
                }
            }
        }
    }

    private fun getInsertedUsername(){
        bindingSearchUser?.apply {
            searchViewUserSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    userViewModel.searchUserOnSubmittedText(p0!!)
                    searchViewUserSearch.clearFocus()
                    searchViewUserSearch.clearAnimation()
                    return true
                }

                override fun onQueryTextChange(p0: String?): Boolean {
                    showLoadingProgress(true)
                    return false
                }

            })
        }
    }

    override fun showLoadingProgress(loadingState: Boolean) {
        when(loadingState){
            true ->bindingSearchUser?.searchProgressBar?.visibility=View.VISIBLE
            false ->bindingSearchUser?.searchProgressBar?.visibility=View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _bindingSearchUser=null
    }

    companion object {

    }

}