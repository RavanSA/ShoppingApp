package android.project.shoppingapp.ui.productdetail

import android.os.Bundle
import android.project.shoppingapp.data.model.Products
import android.project.shoppingapp.databinding.FragmentProductDetailBinding
import android.project.shoppingapp.utils.Constants
import android.project.shoppingapp.utils.Resources
import android.project.shoppingapp.utils.customui.LoadingDialog
import android.project.shoppingapp.utils.customui.showCustomDialog
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

    private val progressBar by lazy {
        LoadingDialog(requireContext())
    }

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
        with(binding) {
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    productDetailViewModel.productState.collect { product ->
                        when (product) {
                            is Resources.Success -> {
                                progressBar.dismiss()
                                tvProductPrice.text =
                                    product.data?.price.toString() + " USD"
                                tvProductTitle.text = product.data?.title.toString()
                                tvProductRating.text = product.data?.ratingRate.toString()
                                tvProductDescription.text =
                                    product.data?.description.toString()
                                tvProductReview.text =
                                    product.data?.ratingCount.toString() + " Reviews"
                                Glide.with(this@ProductDetailFragment)
                                    .load(product.data?.image)
                                    .into(ivProductImage)
                                product.data?.let { getProductQuantity(it) }
                            }
                            is Resources.Loading -> {
                                progressBar.show()
                            }
                            is Resources.Error -> {
                                progressBar.dismiss()
                                product.message?.let {
                                    showCustomDialog(
                                        it, Constants.ERROR_DIALOG, requireContext()
                                    )
                                }
                            }
                            else -> {}
                        }
                    }
                }
            }
        }
    }


    private fun getProductQuantity(product: Products) {
        lifecycleScope.launch {
            productDetailViewModel.productQuantity.collect { quantity ->
                with(binding) {
                    tvProductQuantity.text = quantity?.toString() ?: "0"
                    if (quantity == 0 || quantity == null) {
                        groupQuantitySettings.visibility = View.GONE
                        btnAddtoCart.visibility = View.VISIBLE
                    } else {
                        groupQuantitySettings.visibility = View.VISIBLE
                        btnAddtoCart.visibility = View.GONE
                    }
                    btnAddtoCart.setOnClickListener {
                        if (quantity == 0 || quantity == null) {
                            addToCart(product)
                        }
                    }
                    btnPlus.setOnClickListener {
                        increaseProductQuantity(product.id)
                    }
                    btnMinus.setOnClickListener {
                        if (quantity != 0) decreaseProductQuantity(product.id)
                    }
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