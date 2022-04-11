package xyz.heydarrn.githubuserv2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.add
import androidx.fragment.app.commit
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
}