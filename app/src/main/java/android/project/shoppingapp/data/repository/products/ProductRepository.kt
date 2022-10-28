package android.project.shoppingapp.data.repository.products

import android.project.shoppingapp.data.remote.api.dto.products.ProductDTO
import android.project.shoppingapp.data.remote.api.dto.products.ProductsDTOItem
import android.project.shoppingapp.utils.Resources
import kotlinx.coroutines.flow.Flow

interface ProductRepository {


    fun getProducts(): Flow<Resources<List<ProductsDTOItem>>>

    fun getProductById(productId: Int): Flow<Resources<ProductDTO>>

}