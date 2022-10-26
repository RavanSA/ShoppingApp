package android.project.shoppingapp.ui.authentication.onboarding

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.project.shoppingapp.R
import android.project.shoppingapp.data.model.OnBoardingItem
import android.project.shoppingapp.databinding.FragmentOnBoardingBinding
import android.project.shoppingapp.ui.authentication.onboarding.adapter.OnBoardingItemSliderAdapter
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import kotlinx.coroutines.launch


class OnBoardingFragment : Fragment() {

    private lateinit var binding: FragmentOnBoardingBinding
    private lateinit var navController: NavController


    private val onBoardingIconItems by lazy {
        listOf(
            context?.let { ContextCompat.getDrawable(it, R.drawable.onboarding_1) },
            context?.let { ContextCompat.getDrawable(it, R.drawable.onboarding_2) },
            context?.let { ContextCompat.getDrawable(it, R.drawable.onboarding_3) }
        )
    }

    private val onBoardingItemSliderAdapter by lazy {

        OnBoardingItemSliderAdapter(
            listOf(
                OnBoardingItem(
                    "Purchase Online",
                    "Lorem ipsum dolor sit amet consectetur adipisicing elit. Maxime mollitia,molestiae quas vel sint commodi",
                    onBoardingIconItems[0]
                ),
                OnBoardingItem(
                    "Add to Basket",
                    "Lorem ipsum dolor sit amet consectetur adipisicing elit. Maxime mollitia,molestiae quas vel sint commodi",
                    onBoardingIconItems[1]
                ),
                OnBoardingItem(
                    "Get your Order",
                    "Lorem ipsum dolor sit amet consectetur adipisicing elit. Maxime mollitia,molestiae quas vel sint commodi",
                    onBoardingIconItems[2]
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
        binding.viewPager?.adapter = onBoardingItemSliderAdapter
        binding.onBoardingSkipButton.setOnClickListener {
            //navigate to login
            navController.navigate(
                R.id.action_onBoardingFragment_to_authorizationFragment
            )
        }


//        binding.indicator?.setViewPager(binding?.viewPager)
        binding.viewPager?.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                    super.onPageScrolled(position, positionOffset, positionOffsetPixels)

                    if (position == onBoardingItemSliderAdapter.itemCount - 1) {
                        val animation = AnimationUtils.loadAnimation(
                            requireActivity(),
                            R.anim.splash_textview_anim
                        )

                        binding?.buttonNext?.animation = animation
                        binding?.buttonNext?.text = "Finish"
                        binding.onBoardingSkipButton.visibility = View.GONE

                        binding?.buttonNext?.setOnClickListener {
                            lifecycleScope.launch {
                                //save to data store
                            }

//                            //redirect to login page
                            navController.navigate(
                                R.id.action_onBoardingFragment_to_authorizationFragment
                            )
                        }
                    } else {
                        binding?.buttonNext?.text = "Next"
                    }
                }

                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                }

                override fun onPageScrollStateChanged(state: Int) {
                    super.onPageScrollStateChanged(state)
                }
            }
        )
    }
}