package android.project.shoppingapp.ui.splash

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.project.shoppingapp.R
import android.project.shoppingapp.databinding.ActivitySplashBinding
import android.view.animation.AnimationUtils

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    //viewmodel init


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        splashAnimation()
    }

    private fun splashAnimation() {
        binding.ivSplashLogo.animation = AnimationUtils.loadAnimation(applicationContext,
            R.anim.splash_logo_anim)
        binding.tvSplashAppName.animation = AnimationUtils.loadAnimation(applicationContext,
            R.anim.splash_textview_anim)

    }

    // TODO decide registration flow or app flow

    // TODO fun to start app flow

    // TODO fun to start registration flow

}