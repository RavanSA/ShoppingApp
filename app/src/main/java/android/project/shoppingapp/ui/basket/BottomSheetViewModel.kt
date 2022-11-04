package android.project.shoppingapp.ui.basket

import android.project.shoppingapp.data.local.database.entity.BasketEntity
import android.project.shoppingapp.data.repository.cartrepository.CartRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class BottomSheetViewModel @Inject constructor(
    private val cartRepository: CartRepository
) : ViewModel() {

    private val _basketState: MutableStateFlow<List<BasketEntity>?> =
        MutableStateFlow(null)
    val basketState: StateFlow<List<BasketEntity>?> = _basketState

    private val _productQuantity: MutableStateFlow<Int?> = MutableStateFlow(null)
    val productQuantity: StateFlow<Int?> = _productQuantity

    private val _totalAmount: MutableStateFlow<Double?> = MutableStateFlow(0.0)
    val totalAmount: StateFlow<Double?> = _totalAmount

    init {
        getAllProductsFromBasketByUserId()
        computeTotalAmount()
    }

    private fun getAllProductsFromBasketByUserId() = viewModelScope.launch {
        cartRepository.getAllProductsFromBasketByUserId().collect { basket ->
            _basketState.value = basket
        }
    }

    private fun computeTotalAmount() = viewModelScope.launch(Dispatchers.Default) {
        cartRepository.getAllProductsFromBasketByUserId().collect { basket ->
            var totalPrice: Double = 0.0
            basket.map { item ->
                totalPrice += item.price * item.productQuantity
            }
            _totalAmount.value = totalPrice
        }
    }

    fun increaseProductQuantity(productId: Int) = viewModelScope.launch {
        cartRepository.increaseProductQuantity(productId)
    }

    fun decreaseProductQuantity(productId: Int) = viewModelScope.launch {
        cartRepository.decreaseProductQuantity(productId)
    }


    fun deleteItemFromBasket(productId: Int) = viewModelScope.launch {
        cartRepository.deleteBasketItemById(productId)
    }

    fun deleteAllProductFromBasket() = viewModelScope.launch {
        cartRepository.deleteBasket()
    }

}