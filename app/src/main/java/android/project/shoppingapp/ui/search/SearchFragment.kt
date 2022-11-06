package android.project.shoppingapp.ui.search

import android.os.Bundle
import android.project.shoppingapp.R
import android.project.shoppingapp.data.model.Products
import android.project.shoppingapp.databinding.FragmentSearchBinding
import android.project.shoppingapp.ui.search.adapters.SearchAdapter
import android.project.shoppingapp.ui.search.adapters.SearchItemListener
import android.project.shoppingapp.ui.search.viewmodel.SearchViewModel
import android.project.shoppingapp.utils.Constants
import android.project.shoppingapp.utils.customui.LoadingDialog
import android.project.shoppingapp.utils.customui.showCustomDialog
import android.project.shoppingapp.utils.takeIfError
import android.project.shoppingapp.utils.takeIfLoading
import android.project.shoppingapp.utils.takeIfSuccess
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment(), SearchItemListener {

    private lateinit var binding: FragmentSearchBinding
    private val searchViewModel by viewModels<SearchViewModel>()
    private lateinit var navController: NavController

    private val progressBar by lazy {
        LoadingDialog(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        subscribeCategories()
        subscribeProducts()
        categoriesCallBack()
        observeBasketAmount()
        searchViewTextListener()

        binding.ivSearchCart.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.actionGlobalBasketBottomSheet)
        }

    }

    private fun searchViewTextListener() {
        binding.edSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(p0: String?): Boolean {
                searchViewModel.setSearchQuery(p0 ?: "")
                return false
            }

            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }
        })
    }


    private fun subscribeCategories() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                searchViewModel.categoriesState.collect { categories ->
                    categories?.let {
                        it takeIfSuccess {
                            progressBar.dismiss()
                            it.data?.map { category ->
                                binding.tabLayout.addTab(
                                    binding.tabLayout.newTab().setText(category.category)
                                )
                            }
                        } takeIfLoading {
                            progressBar.show()
                        } takeIfError {
                            progressBar.dismiss()
                            showCustomDialog(
                                it.message ?: Constants.ERROR_TYPE_UNEXPECTED,
                                Constants.ERROR_DIALOG,
                                requireContext()
                            )
                        }
                    } ?: kotlin.run {
                        progressBar.show()
                    }
                }
            }
        }
    }

    private fun categoriesCallBack() {
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                searchViewModel.setCategories(tab?.text.toString())
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }


    private fun observeBasketAmount() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                searchViewModel.totalAmount.collect { amount ->
                    binding.amount = amount.toString()
                }
            }
        }
    }

    private fun subscribeProducts() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                searchViewModel.productsState.collect { products ->
                    binding.rvSearchProducts.adapter =
                        SearchAdapter(this@SearchFragment).apply {
                            differ.submitList(products)
                        }
                }
            }
        }
    }

    override fun onClicked(product: Products) {
        navController.navigate(R.id.action_searchFragment3_to_productDetailFragment,
            Bundle().apply {
                putString(Constants.PRODUCT_ID, product.id.toString())
            }
        )
    }

}