package android.project.shoppingapp.ui.authentication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.project.shoppingapp.R
import android.project.shoppingapp.databinding.ActivityRegistrationBinding
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AuthorizationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initializeNavController()
    }

    private fun initializeNavController(){
        supportFragmentManager
            .findFragmentById(R.id.fragment_container_view) as NavHostFragment
    }

}