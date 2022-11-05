package android.project.shoppingapp.ui.products.viewmodel

import android.project.shoppingapp.data.model.Products
import android.project.shoppingapp.data.remote.api.dto.products.ProductsDTOItem
import android.project.shoppingapp.data.repository.cartrepository.CartRepository
import android.project.shoppingapp.data.repository.categories.CategoriesRepository
import android.project.shoppingapp.data.repository.products.ProductRepository
import android.project.shoppingapp.utils.Resources
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProductViewModel @Inject constructor(
    private val repository: ProductRepository,
    private val cartRepository: CartRepository
): ViewModel() {

    private val _productsState: MutableStateFlow<Resources<List<Products>>?> = MutableStateFlow(null)
    val productsState: StateFlow<Resources<List<Products>>?> = _productsState

    private val _totalAmount: MutableStateFlow<Double?> = MutableStateFlow(0.0)
    val totalAmount: StateFlow<Double?> = _totalAmount

    init {
        getProducts()
        computeBasketAmount()
    }


    fun getProducts(
        getProductsFromRemote: Boolean = false
    ) {
        viewModelScope.launch {
            repository.getProducts(getProductsFromRemote).collect { products ->
                Log.d("PRODUCTS", products?.data.toString())
                _productsState.value = products
            }
        }
    }


    private fun computeBasketAmount() = viewModelScope.launch(Dispatchers.Default) {
        cartRepository.getAllProductsFromBasketByUserId().collect { basket ->
            var totalPrice: Double = 0.0
            basket.map { item ->
                totalPrice += item.price * item.productQuantity
            }
            _totalAmount.value = totalPrice
        }
    }

}