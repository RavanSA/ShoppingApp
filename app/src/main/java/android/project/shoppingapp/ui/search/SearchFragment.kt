package android.project.shoppingapp.ui.search

import android.os.Bundle
import android.project.shoppingapp.R
import android.project.shoppingapp.data.model.Products
import android.project.shoppingapp.databinding.FragmentSearchBinding
import android.project.shoppingapp.ui.search.adapters.SearchAdapter
import android.project.shoppingapp.ui.search.adapters.SearchItemListener
import android.project.shoppingapp.ui.search.viewmodel.SearchViewModel
import android.project.shoppingapp.utils.Resources
import android.util.Log
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
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
        binding.edSearchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextChange(p0: String?): Boolean {
                searchViewModel.setSearchQuery(p0 ?: "")
                return false
            }

            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

        })

        binding.ivSearchCart.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.actionGlobalBasketBottomSheet)
        }
    }


    private fun subscribeCategories() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                searchViewModel.categoriesState.collect { categories ->
                    when (categories) {
                        is Resources.Success -> {
                            categories.data?.map { category ->
                                binding.tabLayout.addTab(
                                    binding.tabLayout.newTab().setText(category.category)
                                )

                                //
                                //                               val chip = Chip(requireContext())
//                                chip.text = category.toString()
//                                binding.productsChipGroup.addView(chip)
                            }
                        }
                        is Resources.Loading -> {}
                        is Resources.Error -> {}
                        else -> {}
                    }
                }
            }
        }
    }

    private fun categoriesCallBack() {
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                Log.d("ONTABSELECTED", tab?.text.toString())
                searchViewModel.setCategories(tab?.text.toString())
//                                        searchViewModel.getProductsByCategory(tab?.text.toString())
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })
    }


    private fun observeBasketAmount() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                searchViewModel.totalAmount.collect { amount ->
                    binding.cartAmount.text = amount.toString() + "$"
                }
            }
        }
    }

    private fun subscribeProducts() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                searchViewModel.productsState.collect { products ->
                    val adapterSearch = SearchAdapter(this@SearchFragment)
                    adapterSearch.differ.submitList(products)
                    binding.rvSearchProducts.adapter = adapterSearch

//                    when (products) {
//                        is Resources.Success -> {
//                            val adapterSearch = SearchAdapter()
//                            adapterSearch.differ.submitList(
//                                products.data
//                            )
//                            binding.rvSearchProducts.adapter = adapterSearch
//                        }
//                        is Resources.Loading -> {}
//                        is Resources.Error -> {}
//                        else -> {}
//                    }
                }
            }
        }
    }

    override fun onClicked(product: Products) {
        navController.navigate(R.id.action_searchFragment3_to_productDetailFragment, Bundle().apply {
            putString("productId", product.id.toString())
        })
    }
}