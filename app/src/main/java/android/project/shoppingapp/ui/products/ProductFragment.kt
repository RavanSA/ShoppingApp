package android.project.shoppingapp.ui.products

import android.os.Bundle
import android.project.shoppingapp.R
import android.project.shoppingapp.data.model.Products
import android.project.shoppingapp.databinding.FragmentProductBinding
import android.project.shoppingapp.ui.products.adapter.NewProductsLists
import android.project.shoppingapp.ui.products.adapter.ProductListener
import android.project.shoppingapp.ui.products.adapter.ProductsAdapter
import android.project.shoppingapp.ui.products.viewmodel.ProductViewModel
import android.project.shoppingapp.utils.Resources
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ProductFragment : Fragment(), ProductListener {

    private lateinit var binding: FragmentProductBinding
    private val productViewModel by viewModels<ProductViewModel>()
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        onRefresh()
        subscribeProductList()
        observeBasketAmount()
        binding.ivProductCart.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.actionGlobalBasketBottomSheet)
        }
    }


    //
    private fun onRefresh() {
        binding.swiperefresh.setOnRefreshListener {
            lifecycleScope.launch {
                productViewModel.getProducts(true)
                delay(2000L)
                binding.swiperefresh.isRefreshing = false
            }
        }
    }


    private fun subscribeProductList() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                productViewModel.productsState.collect { products ->
                    when (products) {
                        is Resources.Success -> {

                            //       binding.rvHourlyList.layoutManager =
                            //         LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                            val adapterNewItem = NewProductsLists()
                            adapterNewItem.differ.submitList(
                                products.data
                                    ?.shuffled()?.take(10)
                            )
//                            binding.rvHourlyList.adapter = adapterNewItem
                            Log.d("PRODUCTS", products.data.toString())
                            val adapter = ProductsAdapter(
                                this@ProductFragment
                            )

                            adapter.differ.submitList(products.data)
                            binding.productRecyclerView.adapter = adapter

//                            BasketBottomSheet(requireContext(), )
//                                .show()

                        }
                        is Resources.Loading -> {}
                        is Resources.Error -> {}
                        else -> {}
                    }
                }
            }
        }
    }

    private fun observeBasketAmount() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                productViewModel.totalAmount.collect { amount ->
                  binding.cartAmount.text = amount.toString() + "$"
                }
            }
        }
    }


    override fun onClicked(product: Products) {
        navController.navigate(R.id.action_productFragment2_to_productDetailFragment, Bundle().apply {
            putString("productId", product.id.toString())
        })
    }

}