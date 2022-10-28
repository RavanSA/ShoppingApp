package android.project.shoppingapp.data.repository.products.impl

import android.project.assignmentweek5.data.local.database.AppDatabase
import android.project.shoppingapp.data.remote.api.ProductsAPI
import android.project.shoppingapp.data.remote.api.dto.products.ProductDTO
import android.project.shoppingapp.data.remote.api.dto.products.ProductsDTOItem
import android.project.shoppingapp.data.repository.products.ProductRepository
import android.project.shoppingapp.utils.Resources
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val api: ProductsAPI,
    private val appDatabase: AppDatabase
) : ProductRepository {


    override fun getProducts(): Flow<Resources<List<ProductsDTOItem>>> = flow {
        emit(Resources.Loading<List<ProductsDTOItem>>(true))
        val products = api.getAllProducts()
        emit(Resources.Success<List<ProductsDTOItem>>(data = products))
    }.catch { error ->
        emit(Resources.Error<List<ProductsDTOItem>>("Error Occurred $error"))
    }.flowOn(Dispatchers.IO)


    override fun getProductById(productId: Int): Flow<Resources<ProductDTO>>
            = flow {
        emit(Resources.Loading<ProductDTO>(true))
        val products = api.getProduct(productId)
        emit(Resources.Success<ProductDTO>(data = products))
    }.catch { error ->
        emit(Resources.Error<ProductDTO>(error.message.toString()))
    }.flowOn(Dispatchers.IO)


}