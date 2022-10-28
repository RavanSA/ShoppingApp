package android.project.shoppingapp.ui.search.viewmodel

import android.project.shoppingapp.data.remote.api.dto.categories.CategoriesDTO
import android.project.shoppingapp.data.remote.api.dto.products.ProductsDTOItem
import android.project.shoppingapp.data.repository.categories.CategoriesRepository
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
class SearchViewModel @Inject constructor(
    private val categoryRepository: CategoriesRepository
) :ViewModel() {

    private val _categoriesState: MutableStateFlow<Resources<List<CategoriesDTO>>?> = MutableStateFlow(null)
    val categoriesState: StateFlow<Resources<List<CategoriesDTO>>?> = _categoriesState


    init {
        getAllCategories()
    }

    private fun getAllCategories() = viewModelScope.launch {
        categoryRepository.getAllCategories().collect { categories ->
            Log.d("CATEGORIES", categories.toString())
            _categoriesState.value = categories
        }
    }


}