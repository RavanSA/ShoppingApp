package android.project.shoppingapp.ui.products

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.project.shoppingapp.R
import android.project.shoppingapp.databinding.FragmentProductBinding
import android.project.shoppingapp.ui.products.adapter.NewProductsLists
import android.project.shoppingapp.ui.products.adapter.ProductsAdapter
import android.project.shoppingapp.ui.products.viewmodel.ProductViewModel
import android.project.shoppingapp.utils.Resources
import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ProductFragment : Fragment() {

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
        subscribeProductList()
    }

    private fun subscribeProductList() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                productViewModel.productsState.collect { products ->
                    when (products) {
                        is Resources.Success -> {

                            binding.rvHourlyList.layoutManager =
                                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                            val adapterNewItem = NewProductsLists()
                            adapterNewItem.differ.submitList(
                                products.data
                                    ?.shuffled()?.take(10)
                            )
                            binding.rvHourlyList.adapter = adapterNewItem
                            Log.d("PRODUCTS", products.data.toString())
                            val adapter = ProductsAdapter()

                            adapter.differ.submitList(products.data)
                            binding.productRecyclerView.adapter = adapter

                        }
                        is Resources.Loading -> {}
                        is Resources.Error -> {}
                        else -> {}
                    }
                }
            }
        }
    }

}