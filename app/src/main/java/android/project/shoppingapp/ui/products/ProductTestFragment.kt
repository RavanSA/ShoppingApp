package android.project.shoppingapp.ui.products

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.project.shoppingapp.R
import android.project.shoppingapp.databinding.FragmentProductTestBinding
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ProductTestFragment : Fragment() {

    private lateinit var binding: FragmentProductTestBinding

//    private lateinit var navController: NavController
//    private val navController by lazy {
//        val navHostFragment =
//            childFragmentManager.findFragmentById(R.id.product_fragment_container) as NavHostFragment?
//        ?: return@lazy
//        navHostFragment.navController
//    }
//    private val navController by lazy {
//        val navHostFragment =
//            supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment
//        navHostFragment.navController
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductTestBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val finalHost = NavHostFragment.create(R.navigation.app_nav_graph)
//        childFragmentManager.beginTransaction()
//            .replace(R.id.main_nav_graph, finalHost)
//            .setPrimaryNavigationFragment(finalHost) // equivalent to app:defaultNavHost="true"
//            .commit()
//        var navController :NavController= findNavController()
//        binding.bottomNavigationView.setupWithNavController(navController)
//        navController.setGraph(R.navigation.app_nav_graph)
//        navController.navigate(R.id.)
//        initializeBottomNav()
    }
//
//    private fun initializeBottomNav() {
//        val navController = this.findNavController()
//        binding.bottomNavigationView.setupWithNavController(navController)
//    }

}