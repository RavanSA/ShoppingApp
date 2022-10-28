package android.project.shoppingapp.ui.splash

import android.os.Bundle
import android.project.shoppingapp.R
import android.project.shoppingapp.databinding.FragmentSplashBinding
import android.project.shoppingapp.ui.splash.viewmodel.SplashScreenEvent
import android.project.shoppingapp.ui.splash.viewmodel.SplashViewModel
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashFragment : Fragment() {

    private lateinit var binding: FragmentSplashBinding
    private lateinit var navController: NavController
    private val splashViewModel by viewModels<SplashViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSplashBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        splashAnimation()
        navController = findNavController()
        redirectToUser()
    }


    private fun splashAnimation() {
        binding.ivSplashLogo.animation = AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.splash_logo_anim
        )
        binding.pbActivityIndicator.animation = AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.splash_textview_anim
        )
    }

    private fun redirectToUser() {
        lifecycleScope.launchWhenResumed {
            splashViewModel.authEvent.collect {
                when (it) {
                    is SplashScreenEvent.RedirectToRegistrationFlow -> {
                        startAuthorization()
                    }
                    is SplashScreenEvent.RedirectToApplicationFlow -> {
                        startApplication()
                    }
                    is SplashScreenEvent.RedirectToOnBoardingScreen -> {
                        startOnBoardScreen()
                    }
//                    is SplashScreenEvent.RedirectToOnBoardingScreen -> {
//                        startOnBoardScreen()
//                    }

                }
            }
        }
    }

    private fun startOnBoardScreen() {
        lifecycleScope.launch {
            delay(3000L)
            navController.navigate(R.id.action_splashFragment3_to_onBoardingFragment3)
        }
    }

//    private fun startOnBoardScreen() {
//        lifecycleScope.launch {
//            delay(3000L)
//            navController.navigate(R.id.action_splashFragment_to_onBoardingFragment)
//        }
//    }

    private fun startAuthorization() {
        lifecycleScope.launch {
            delay(3000L)
            navController.navigate(R.id.action_splashFragment3_to_authorizationFragment3)
        }
    }

    private fun startApplication() {
        lifecycleScope.launch {
            delay(3000L)
            navController.navigate(R.id.action_splashFragment3_to_productFragment2)
        }
    }

}

