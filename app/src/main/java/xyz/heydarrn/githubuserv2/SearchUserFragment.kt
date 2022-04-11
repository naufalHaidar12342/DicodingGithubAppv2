package xyz.heydarrn.githubuserv2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import xyz.heydarrn.githubuserv2.databinding.FragmentSearchUserBinding


class SearchUserFragment : Fragment() {
    private var _bindingSearchUser:FragmentSearchUserBinding? = null
    private val bindingSearchUser get() = _bindingSearchUser

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _bindingSearchUser= FragmentSearchUserBinding.inflate(inflater,container,false)
        return bindingSearchUser?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _bindingSearchUser=null
    }

    companion object {

    }
}