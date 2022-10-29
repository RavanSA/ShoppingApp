package android.project.shoppingapp.data.repository.categories.impl

import android.project.assignmentweek5.data.local.database.AppDatabase
import android.project.shoppingapp.data.remote.api.CategoriesAPI
import android.project.shoppingapp.data.remote.api.ProductsAPI
import android.project.shoppingapp.data.remote.api.dto.categories.CategoriesDTO
import android.project.shoppingapp.data.remote.api.dto.products.ProductDTO
import android.project.shoppingapp.data.remote.api.dto.products.ProductsDTOItem
import android.project.shoppingapp.data.repository.categories.CategoriesRepository
import android.project.shoppingapp.data.repository.products.ProductRepository
import android.project.shoppingapp.utils.Resources
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CategoriesRepositoryImpl@Inject constructor(
    private val api: CategoriesAPI,
    private val appDatabase: AppDatabase
) : CategoriesRepository {


    override fun getAllCategories(): Flow<Resources<CategoriesDTO>> = flow {
        emit(Resources.Loading<CategoriesDTO>(true))
        val categories = api.getAllCategories()
        emit(Resources.Success<CategoriesDTO>(data = categories))
    }.catch { error ->
        Log.d("CATEGORIES", error.toString())
        emit(Resources.Error<CategoriesDTO>(error.message.toString()))
    }.flowOn(Dispatchers.IO)


    override fun getProductsByCategory(category: String) : Flow<Resources<List<ProductsDTOItem>>>
            = flow {
        emit(Resources.Loading<List<ProductsDTOItem>>(true))
        val products = api.getProductsByCategory(category)
        emit(Resources.Success<List<ProductsDTOItem>>(data = products))
    }.catch { error ->
        emit(Resources.Error<List<ProductsDTOItem>>(error.message.toString()))
    }.flowOn(Dispatchers.IO)


}