package android.project.shoppingapp.ui.authentication.onboarding

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
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import kotlinx.coroutines.launch


class OnBoardingFragment : Fragment() {

    private lateinit var binding: FragmentOnBoardingBinding
    private lateinit var navController: NavController

    private val onBoardingItemSliderAdapter = OnBoardingItemSliderAdapter(
        listOf(
            OnBoardingItem(
                "Health Tips / Advice",
                "Discover tips and advice to help you to help maintain transform and main your health",
                "exercise.json"
            ),
            OnBoardingItem(
                "Diet Tips / Advice",
                "Find out basics of health diet and good nutrition, Start eating well and keep a balanced diet",
                "diet.json"
            ),
            OnBoardingItem(
                "Covid 19 Symptoms/Prevention tips",
                "Get regular Reminders of Covid-19 prevention tips ensuring you stay safe",
                "covid19.json"
            )
        )
    )

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