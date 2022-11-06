package android.project.shoppingapp.data.repository.categories

import android.project.shoppingapp.data.local.database.entity.ProductsEntity
import android.project.shoppingapp.data.model.Category
import android.project.shoppingapp.data.model.Products
import android.project.shoppingapp.data.remote.api.dto.categories.CategoriesDTO
import android.project.shoppingapp.data.remote.api.dto.products.ProductsDTOItem
import android.project.shoppingapp.utils.Resources
import kotlinx.coroutines.flow.Flow

interface CategoriesRepository {

    fun getAllCategories() : Flow<Resources<List<Category>>>

    fun getProductsByCategory(category : String) : Flow<Resources<List<ProductsDTOItem>>>

}