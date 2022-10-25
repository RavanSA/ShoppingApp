package android.project.shoppingapp.ui.splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.project.shoppingapp.R
import android.project.shoppingapp.databinding.ActivitySplashBinding
import android.project.shoppingapp.utils.navgraph.ActivityNavGraph
import android.view.WindowManager
import android.view.animation.AnimationUtils
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    //viewmodel init


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        splashAnimation()
        startRegistrationActivity()
    }

    private fun splashAnimation() {
        binding.ivSplashLogo.animation = AnimationUtils.loadAnimation(applicationContext,
            R.anim.splash_logo_anim)
        binding.tvSplashAppName.animation = AnimationUtils.loadAnimation(applicationContext,
            R.anim.splash_textview_anim)

    }

    private fun startRegistrationActivity() {
        lifecycleScope.launch {
            delay(3000L)
            ActivityNavGraph.startLoginAndRegisterActivity(applicationContext)
        }
    }

    // TODO decide registration flow or app flow

    // TODO fun to start app flow

    // TODO fun to start registration flow

}