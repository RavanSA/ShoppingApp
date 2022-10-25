package android.project.shoppingapp.ui.authentication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.project.shoppingapp.R
import androidx.navigation.fragment.NavHostFragment

class AuthorizationActivity : AppCompatActivity() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        initializeNavController()
    }

    private fun initializeNavController(){
        supportFragmentManager
            .findFragmentById(R.id.fragment_container_view) as NavHostFragment
    }

    // if user first time redirect to onboard else login page


}