package android.project.shoppingapp.ui.profile

import android.project.shoppingapp.data.local.DataStoreManager
import android.project.shoppingapp.data.model.Products
import android.project.shoppingapp.data.model.User
import android.project.shoppingapp.data.repository.cartrepository.CartRepository
import android.project.shoppingapp.data.repository.firebase.AuthRepository
import android.project.shoppingapp.utils.Resources
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.toSet
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val firebaseRepository: AuthRepository,
    private val dataStoreManager: DataStoreManager,
    private val cartRepository: CartRepository
) : ViewModel() {

    private val _profileInfo: MutableStateFlow<ProfileState?> =
        MutableStateFlow(ProfileState.Loading)
    val profileInfo: StateFlow<ProfileState?> = _profileInfo

    private val _totalAmount: MutableStateFlow<Double?> = MutableStateFlow(0.0)
    val totalAmount: StateFlow<Double?> = _totalAmount

    private var currentUser: String = ""


    init {
        getUserId()
        getUserInfo()
        computeBasketAmount()
    }

    private fun getUserId() = viewModelScope.launch {
        dataStoreManager.userId.collect { uid ->
            currentUser = uid
        }
    }

    private fun getUserInfo() = viewModelScope.launch {
        _profileInfo.value = ProfileState.Loading
        firebaseRepository.getUserProfileInfo()
            .catch { ProfileState.Error(it.toString()) }
            .collect { user ->
                _profileInfo.value = user?.let { ProfileState.Success(user = it) }
            }
    }

    fun logout() = viewModelScope.launch {
        firebaseRepository.logout()
        dataStoreManager.updateUserAuthentication(false)
    }

    private fun computeBasketAmount() = viewModelScope.launch {
        cartRepository.getAllProductsFromBasketByUserId(currentUser).collect { basket ->
            var totalPrice: Double = 0.0
            basket.map { item ->
                totalPrice += item.price * item.productQuantity
            }
            _totalAmount.value = totalPrice
        }
    }

}

sealed class ProfileState {
    class Success(val user: User) : ProfileState()
    object Loading : ProfileState()
    data class Error(val message: String) : ProfileState()
}
