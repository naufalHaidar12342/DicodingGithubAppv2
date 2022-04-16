package xyz.heydarrn.githubuserv2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import xyz.heydarrn.githubuserv2.databinding.FragmentThemeSettingBinding

class ThemeSettingFragment : Fragment() {
    private var _bindThemeSetting:FragmentThemeSettingBinding?=null
    private val bindThemeSetting get() = _bindThemeSetting
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _bindThemeSetting= FragmentThemeSettingBinding.inflate(inflater,container,false)
        return bindThemeSetting?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState!=null){
            childFragmentManager.commit {
                setReorderingAllowed(true)
                replace<ThemeSettingFragment>(R.id.fragment_container)
                addToBackStack(null)
            }
        }
    }

}