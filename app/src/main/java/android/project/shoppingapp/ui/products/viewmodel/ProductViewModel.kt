package android.project.shoppingapp.ui.products.viewmodel

import android.project.shoppingapp.data.model.Products
import android.project.shoppingapp.data.remote.api.dto.products.ProductsDTOItem
import android.project.shoppingapp.data.repository.categories.CategoriesRepository
import android.project.shoppingapp.data.repository.products.ProductRepository
import android.project.shoppingapp.utils.Resources
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProductViewModel @Inject constructor(
    private val repository: ProductRepository
): ViewModel() {

    private val _productsState: MutableStateFlow<Resources<List<Products>>?> = MutableStateFlow(null)
    val productsState: StateFlow<Resources<List<Products>>?> = _productsState

    init {
        getProducts()
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

}