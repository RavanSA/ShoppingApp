package android.project.shoppingapp.ui.productdetail

import android.os.Bundle
import android.project.shoppingapp.data.model.Products
import android.project.shoppingapp.databinding.FragmentProductDetailBinding
import android.project.shoppingapp.utils.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ProductDetailFragment : Fragment() {

    private lateinit var binding: FragmentProductDetailBinding
    private val productDetailViewModel by viewModels<ProductDetailViewModel>()
    private lateinit var navController: NavController


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        subscribeProductDetail()
        navigateToBack()
    }

    private fun subscribeProductDetail() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                productDetailViewModel.productState.collect { product ->
                    when (product) {
                        is Resources.Success -> {
                            binding.tvProductPrice.text = product.data?.price.toString() + " USD"
                            binding.tvProductTitle.text = product.data?.title.toString()
                            binding.tvProductRating.text = product.data?.ratingRate.toString()
                            binding.tvProductDescription.text =
                                product.data?.description.toString()
                            binding.tvProductReview.text = product.data?.ratingCount.toString() + " Reviews"
                            Glide.with(this@ProductDetailFragment)
                                .load(product.data?.image)
                                .into(binding.ivProductImage)
                            product.data?.let { getProductQuantity(it) }
                        }
                        is Resources.Loading -> {}
                        is Resources.Error -> {}
                        else -> {}
                    }
                }
            }
        }
    }


    private fun getProductQuantity(product: Products) {
        lifecycleScope.launch {
            productDetailViewModel.productQuantity.collect { quantity ->
                binding.tvProductQuantity.text = quantity?.toString() ?: "0"
                if (quantity == 0 || quantity == null) {
                    binding.groupQuantitySettings.visibility = View.GONE
                    binding.btnAddtoCart.visibility = View.VISIBLE
                } else {
                    binding.groupQuantitySettings.visibility = View.VISIBLE
                    binding.btnAddtoCart.visibility = View.GONE
                }
                binding.btnAddtoCart.setOnClickListener {
                    if (quantity == 0 || quantity == null) {
                        addToCart(product)
                    }
                }
                binding.btnPlus.setOnClickListener {
                    increaseProductQuantity(product.id)
                }
                binding.btnMinus.setOnClickListener {
                    if (quantity != 0) decreaseProductQuantity(product.id)
                }
            }
        }
    }

    private fun addToCart(product: Products) {
        productDetailViewModel.insertProductToBasket(product)
    }

    private fun increaseProductQuantity(productId: Int) {
        productDetailViewModel.increaseProductQuantity(productId)
    }

    private fun decreaseProductQuantity(productId: Int) {
        productDetailViewModel.decreaseProductQuantity(productId)
    }


    fun navigateToBack() {
        binding.ivBackButton.setOnClickListener {
            navController.popBackStack()
        }
    }

}