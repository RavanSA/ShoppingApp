package android.project.shoppingapp.ui.splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.project.shoppingapp.R
import android.project.shoppingapp.databinding.ActivitySplashBinding
import android.project.shoppingapp.ui.splash.viewmodel.SplashScreenEvent
import android.project.shoppingapp.ui.splash.viewmodel.SplashViewModel
import android.project.shoppingapp.utils.navgraph.ActivityNavGraph
import android.view.WindowManager
import android.view.animation.AnimationUtils
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private val splashViewModel by viewModels<SplashViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        splashAnimation()
        redirectToUser()
    }

    private fun splashAnimation() {
        binding.ivSplashLogo.animation = AnimationUtils.loadAnimation(applicationContext,
            R.anim.splash_logo_anim)
        binding.pbActivityIndicator.animation = AnimationUtils.loadAnimation(applicationContext,
            R.anim.splash_textview_anim)
    }


    private fun redirectToUser() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                splashViewModel.authEvent.collect { event ->
                    when (event) {
                        is SplashScreenEvent.RedirectToRegistrationFlow -> {
                            startRegistrationActivity()
                        }
                        is SplashScreenEvent.RedirectToApplicationFlow -> {
                            startApplicationFlow()
                        }
                    }
                }
            }
        }
    }

    private fun startRegistrationActivity() {
        lifecycleScope.launch {
            delay(3000L)
            ActivityNavGraph.startRegistrationFlow(applicationContext)
        }
    }

    private fun startApplicationFlow() {
        lifecycleScope.launch {
            delay(3000L)
            ActivityNavGraph.startApplicationFlow(this@SplashActivity, applicationContext)
        }
    }

}