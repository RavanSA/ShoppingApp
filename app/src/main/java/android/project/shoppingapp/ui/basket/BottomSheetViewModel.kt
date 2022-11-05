package android.project.shoppingapp.ui.basket

import android.project.shoppingapp.data.local.DataStoreManager
import android.project.shoppingapp.data.local.database.entity.BasketEntity
import android.project.shoppingapp.data.repository.cartrepository.CartRepository
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
class BottomSheetViewModel @Inject constructor(
    private val cartRepository: CartRepository,
    private val dataStore: DataStoreManager
) : ViewModel() {

    private val _basketState: MutableStateFlow<List<BasketEntity>?> =
        MutableStateFlow(null)
    val basketState: StateFlow<List<BasketEntity>?> = _basketState

    private val _productQuantity: MutableStateFlow<Int?> = MutableStateFlow(null)
    val productQuantity: StateFlow<Int?> = _productQuantity

    private val _totalAmount: MutableStateFlow<Double?> = MutableStateFlow(0.0)
    val totalAmount: StateFlow<Double?> = _totalAmount

    private var currentUser: String = ""

    init {
        getUserId()
        getAllProductsFromBasketByUserId()
        computeTotalAmount()
    }

    private fun getUserId() = viewModelScope.launch {
        dataStore.userId.collect { uid ->
            currentUser = uid
        }
    }

    private fun getAllProductsFromBasketByUserId() = viewModelScope.launch {
        cartRepository.getAllProductsFromBasketByUserId(userId = currentUser).collect { basket ->
            _basketState.value = basket
        }
    }

    private fun computeTotalAmount() = viewModelScope.launch {
        Log.d("UIDAMOUNT", currentUser)

        cartRepository.getAllProductsFromBasketByUserId(currentUser).collect { basket ->
            var totalPrice: Double = 0.0
            Log.d("BASKET", basket.toString())
            basket.map { item ->
                Log.d("ITEMBASKETAMOUNT", item.toString())
                totalPrice += item.price * item.productQuantity
            }
            _totalAmount.value = totalPrice
        }
    }

    fun increaseProductQuantity(productId: Int) = viewModelScope.launch {
        cartRepository.increaseProductQuantity(productId, currentUser)
    }

    fun decreaseProductQuantity(productId: Int) = viewModelScope.launch {
        cartRepository.decreaseProductQuantity(productId, currentUser)
    }


    fun deleteItemFromBasket(productId: Int) = viewModelScope.launch {
        cartRepository.deleteBasketItemById(productId, currentUser)
    }

    fun deleteAllProductFromBasket() = viewModelScope.launch {
        cartRepository.deleteBasket(currentUser)
    }

}