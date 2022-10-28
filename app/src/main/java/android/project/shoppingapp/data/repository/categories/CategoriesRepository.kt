package android.project.shoppingapp.data.repository.categories

import android.project.shoppingapp.data.remote.api.dto.categories.CategoriesDTO
import android.project.shoppingapp.data.remote.api.dto.products.ProductsDTOItem
import android.project.shoppingapp.utils.Resources
import kotlinx.coroutines.flow.Flow

interface CategoriesRepository {


    fun getAllCategories() :  Flow<Resources<List<CategoriesDTO>>>

    fun getProductsByCategory(category : String) : Flow<Resources<List<ProductsDTOItem>>>


}