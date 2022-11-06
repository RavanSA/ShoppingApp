package android.project.shoppingapp.ui.authentication.onboarding

import android.os.Bundle
import android.project.shoppingapp.R
import android.project.shoppingapp.data.model.OnBoardingItem
import android.project.shoppingapp.databinding.FragmentOnBoardingBinding
import android.project.shoppingapp.ui.authentication.onboarding.adapter.OnBoardingItemSliderAdapter
import android.project.shoppingapp.ui.authentication.onboarding.viewmodel.OnBoardingViewModel
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OnBoardingFragment : Fragment() {

    private lateinit var binding: FragmentOnBoardingBinding
    private lateinit var navController: NavController
    private val onBoardingViewModel by viewModels<OnBoardingViewModel>()


    private val onBoardingIconItems by lazy {
        context?.let {
            listOf(
                ContextCompat.getDrawable(it, R.drawable.onboarding_1),
                ContextCompat.getDrawable(it, R.drawable.onboarding_2),
                ContextCompat.getDrawable(it, R.drawable.onboarding_3)
            )
        }
    }

    private val onBoardingItemSliderAdapter by lazy {

        OnBoardingItemSliderAdapter(
            listOf(
                OnBoardingItem(
                    "Purchase Online",
                    "Lorem ipsum dolor sit amet consectetur adipisicing elit. Maxime mollitia,molestiae quas vel sint commodi",
                    onBoardingIconItems?.get(0)
                ),
                OnBoardingItem(
                    "Add to Basket",
                    "Lorem ipsum dolor sit amet consectetur adipisicing elit. Maxime mollitia,molestiae quas vel sint commodi",
                    onBoardingIconItems?.get(1)
                ),
                OnBoardingItem(
                    "Get your Order",
                    "Lorem ipsum dolor sit amet consectetur adipisicing elit. Maxime mollitia,molestiae quas vel sint commodi",
                    onBoardingIconItems?.get(2)
                )
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOnBoardingBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        with(binding) {
            viewPager.adapter = onBoardingItemSliderAdapter
            viewPager.registerOnPageChangeCallback(onBoardingPageChangeCallback)

            onBoardingSkipButton.setOnClickListener {
                navController.navigate(
                    R.id.action_onBoardingFragment3_to_authorizationFragment3
                )
            }

        }
    }


    private fun updateCircleMarker(binding: FragmentOnBoardingBinding, position: Int) {
        with(binding) {
            when (position) {
                0 -> {
                    onBoardingInitialCircle.background =
                        ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.selected_dot_viewpager
                        )
                    onBoardingMiddleCircle.background =
                        ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.default_dot_viewpager
                        )
                    binding.onBoardingLastCircle.background =
                        ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.default_dot_viewpager
                        )
                }
                1 -> {
                    onBoardingInitialCircle.background =
                        ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.default_dot_viewpager
                        )
                    onBoardingMiddleCircle.background =
                        ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.selected_dot_viewpager
                        )
                    onBoardingLastCircle.background =
                        ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.default_dot_viewpager
                        )
                }
                2 -> {
                    onBoardingInitialCircle.background =
                        ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.default_dot_viewpager
                        )
                    onBoardingMiddleCircle.background =
                        ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.default_dot_viewpager
                        )
                    onBoardingLastCircle.background =
                        ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.selected_dot_viewpager
                        )
                }
            }
        }
    }

    private var onBoardingPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
            super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            with(binding) {
                if (position == 0) {
                    ivskipIcon.visibility = View.INVISIBLE
                    onboardBackButton.visibility = View.INVISIBLE
                } else {
                    onboardBackButton.visibility = View.VISIBLE
                }

                if (position == onBoardingItemSliderAdapter.itemCount - 1) {
                    val animation = AnimationUtils.loadAnimation(
                        requireActivity(),
                        R.anim.splash_textview_anim
                    )

                    onboardForwardButton.animation = animation
                    onboardForwardButton.text = "Finish"
                    onBoardingSkipButton.visibility = View.INVISIBLE
                    ivskipIcon.visibility = View.INVISIBLE

                    onboardForwardButton.setOnClickListener {
                        lifecycleScope.launch {
                            onBoardingViewModel.userPassedOnBoardScreens()
                        }

                        navController.navigate(
                            R.id.action_onBoardingFragment3_to_authorizationFragment3
                        )
                    }
                } else {
                    onBoardingSkipButton.visibility = View.VISIBLE
                    ivskipIcon.visibility = View.VISIBLE
                    onboardForwardButton?.text = ""
                }
            }
        }

        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            updateCircleMarker(binding, position)
            binding.onboardForwardButton.setOnClickListener {
                binding.viewPager.currentItem = binding.viewPager.currentItem + 1
            }
            binding.onboardBackButton.setOnClickListener {
                binding.viewPager.currentItem = binding.viewPager.currentItem - 1

            }
        }

        override fun onPageScrollStateChanged(state: Int) {
            super.onPageScrollStateChanged(state)
        }

    }


    override fun onDestroyView() {
        binding.viewPager.unregisterOnPageChangeCallback(onBoardingPageChangeCallback)
        super.onDestroyView()
    }

}


