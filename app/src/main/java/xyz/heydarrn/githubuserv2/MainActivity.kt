package xyz.heydarrn.githubuserv2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import xyz.heydarrn.githubuserv2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var bindMainActivity : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bindMainActivity= ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindMainActivity.root)

        if (savedInstanceState==null){
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<SearchUserFragment>(R.id.fragment_container)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val optionMenuInflater:MenuInflater=menuInflater
        optionMenuInflater.inflate(R.menu.option_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.favourite_user_option ->{
                supportFragmentManager.commit {
                    setReorderingAllowed(true)
                    replace<FavouriteUserFragment>(R.id.fragment_container)
                    addToBackStack(null)
                }
                return true
            }
            R.id.theme_change_option->{
                supportFragmentManager.commit {
                    setReorderingAllowed(true)
                    replace<ThemeSettingFragment>(R.id.fragment_container)
                    addToBackStack(null)
                }
                return true
            }
            else ->return true
        }
    }
}