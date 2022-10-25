package android.project.shoppingapp.ui.authorization.registration.adapter

import android.project.shoppingapp.ui.authorization.registration.LoginFragment
import android.project.shoppingapp.ui.authorization.registration.RegistrationFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

private const val NUM_TABS = 2

class AuthenticationAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return LoginFragment()
            1 -> return RegistrationFragment()
        }
        return LoginFragment()
    }
}