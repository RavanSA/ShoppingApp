package android.project.shoppingapp.data.repository.products

import android.project.shoppingapp.data.model.Products
import android.project.shoppingapp.data.remote.api.dto.products.ProductDTO
import android.project.shoppingapp.data.remote.api.dto.products.ProductsDTOItem
import android.project.shoppingapp.utils.Resources
import kotlinx.coroutines.flow.Flow

interface ProductRepository {


    fun getProducts(fetchProductListFromRemote: Boolean): Flow<Resources<List<Products>>?>

    fun getProductById(productId: Int): Flow<Resources<Products>>

    fun getProductsBySearchFilters(category: String?, searchQuery: String) : Flow<List<Products>>


}