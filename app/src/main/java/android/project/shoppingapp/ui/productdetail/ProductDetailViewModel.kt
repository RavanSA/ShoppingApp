package android.project.shoppingapp.ui.productdetail

import android.project.shoppingapp.data.local.database.entity.BasketEntity
import android.project.shoppingapp.data.model.Category
import android.project.shoppingapp.data.model.Products
import android.project.shoppingapp.data.repository.cartrepository.CartRepository
import android.project.shoppingapp.data.repository.products.ProductRepository
import android.project.shoppingapp.utils.Resources
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _productState: MutableStateFlow<Resources<Products>?> =
        MutableStateFlow(null)
    val productState: StateFlow<Resources<Products>?> = _productState

//    private val _product: MutableStateFlow<Products?> = MutableStateFlow(null)
    private val _productQuantity: MutableStateFlow<Int?> = MutableStateFlow(0)
    val productQuantity: StateFlow<Int?> = _productQuantity

    init {
        savedStateHandle.get<String>("productId")?.let { productId ->
            getProductById(productId.toInt())
            getProductQuantity(productId.toInt())
        }
    }

    private fun getProductById(id: Int) = viewModelScope.launch {
        productRepository.getProductById(id).collect { product ->
            _productState.value = product
        }
    }

    fun insertProductToBasket(product: Products) = viewModelScope.launch {
        val basket = BasketEntity(
            category = product.category,
            description = product.description,
            productId = product.id,
            image = product.image,
            price = product.price,
            ratingCount = product.ratingCount,
            ratingRate = product.ratingRate,
            title = product.title,
            productQuantity = 1,
            userId = ""
        )

        cartRepository.insertProductToBasket(product = basket)
    }


    fun increaseProductQuantity(productId: Int) = viewModelScope.launch {
        if(productQuantity.value != 0) {
            cartRepository.increaseProductQuantity(productId)
        }
    }

    fun decreaseProductQuantity(productId: Int) = viewModelScope.launch {
        if(productQuantity.value!! > 0) {
            cartRepository.decreaseProductQuantity(productId)
        }
    }

    private fun getProductQuantity(productId: Int) = viewModelScope.launch {
        cartRepository.getProductQuantity(productId).collect { productQuantity->
            _productQuantity.value = productQuantity
        }
    }

}