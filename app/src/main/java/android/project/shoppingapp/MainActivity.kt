package android.project.shoppingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.project.shoppingapp.databinding.ActivityMainBinding
import android.view.View
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val navController by lazy {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment
        navHostFragment.navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initializeBottomNav()
    }

    private fun initializeBottomNav() {
        with(binding) {
            bottomNavigationView.setupWithNavController(navController)
            navController.addOnDestinationChangedListener { controller, destination, arguments ->
                when (destination.id) {
                    R.id.productFragment2 -> {
                        bottomNavigationView.visibility = View.VISIBLE
                    }
                    R.id.profileFragment3 -> {
                        bottomNavigationView.visibility = View.VISIBLE
                    }
                    R.id.searchFragment3 -> {
                        bottomNavigationView.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    fun hideBottomNav(visible: Boolean) {
        binding.bottomNavigationView.isVisible = visible
    }

}