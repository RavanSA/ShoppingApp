package android.project.shoppingapp.ui.search.viewmodel

import android.project.shoppingapp.data.local.DataStoreManager
import android.project.shoppingapp.data.model.Category
import android.project.shoppingapp.data.model.Products
import android.project.shoppingapp.data.remote.api.dto.categories.CategoriesDTO
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
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor(
    private val categoryRepository: CategoriesRepository,
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
    private val dataStore: DataStoreManager
) : ViewModel() {

    private val _categoriesState: MutableStateFlow<Resources<List<Category>>?> =
        MutableStateFlow(null)
    val categoriesState: StateFlow<Resources<List<Category>>?> = _categoriesState

    private val _productsState: MutableStateFlow<List<Products>?> = MutableStateFlow(null)
    val productsState: StateFlow<List<Products>?> = _productsState

    private val _searchQuery: MutableStateFlow<String> = MutableStateFlow("")
    private val _categories: MutableStateFlow<String> = MutableStateFlow("")

    private val _totalAmount: MutableStateFlow<Double?> = MutableStateFlow(0.0)
    val totalAmount: StateFlow<Double?> = _totalAmount

    private var searchJob: Job? = null

    private var currentUser: String = ""

    init {
        getUserId()
        getAllCategories()
        getProductsBySearchCategories()
        computeBasketAmount()
    }

    private fun getUserId() = viewModelScope.launch{
        dataStore.userId.collect { uid ->
            currentUser = uid
        }
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
        if (query.length > 2 || query.isEmpty()) {
            searchJob?.cancel()
            searchJob = viewModelScope.launch {
                delay(500L)
                getProductsBySearchCategories(searchQuery = _searchQuery.value)
            }
        }
    }

    fun setCategories(category: String) {
        _categories.value = category
        getProductsBySearchCategories(
            category = if (category == "All") null else _categories.value
        )
    }

    private fun getAllCategories() = viewModelScope.launch {
        categoryRepository.getAllCategories().collect { categories ->
            _categoriesState.value = categories
        }
    }

//    fun getProductsByCategory(category: String) = viewModelScope.launch {
//        categoryRepository.getProductsByCategory(category).collect { products ->
//            _productsState.value = products
//        }
//    }

    private fun getProductsBySearchCategories(
        category: String? = null,
        searchQuery: String = _searchQuery.value
    ) = viewModelScope.launch {
        productRepository.getProductsBySearchFilters(
            category = category,
            searchQuery = searchQuery
        ).collect { products ->
            _productsState.value = products
        }
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