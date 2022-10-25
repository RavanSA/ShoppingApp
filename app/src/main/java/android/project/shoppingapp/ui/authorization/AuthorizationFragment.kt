package android.project.shoppingapp.ui.authorization

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.project.shoppingapp.R
import android.project.shoppingapp.databinding.FragmentAuthorizationBinding
import android.project.shoppingapp.ui.authorization.registration.adapter.AuthenticationAdapter
import androidx.fragment.app.FragmentActivity
import com.google.android.material.tabs.TabLayoutMediator


val TAB_ARRAY = arrayOf(
    "Login",
    "Registration"
)

class AuthorizationFragment : Fragment() {

    private lateinit var binding: FragmentAuthorizationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAuthorizationBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewPager = binding.viewPager
        val tabLayout = binding.tabLayout
        val fragmentManager = activity?.supportFragmentManager
        val adapter = fragmentManager?.let { AuthenticationAdapter(it,lifecycle) }
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = TAB_ARRAY[position]
        }.attach()
    }

}