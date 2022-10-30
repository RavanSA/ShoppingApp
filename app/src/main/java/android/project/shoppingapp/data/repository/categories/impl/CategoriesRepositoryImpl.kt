package android.project.shoppingapp.data.repository.categories.impl

import android.project.assignmentweek5.data.local.database.AppDatabase
import android.project.shoppingapp.data.model.Category
import android.project.shoppingapp.data.model.Products
import android.project.shoppingapp.data.remote.api.CategoriesAPI
import android.project.shoppingapp.data.remote.api.ProductsAPI
import android.project.shoppingapp.data.remote.api.dto.categories.CategoriesDTO
import android.project.shoppingapp.data.remote.api.dto.products.ProductDTO
import android.project.shoppingapp.data.remote.api.dto.products.ProductsDTOItem
import android.project.shoppingapp.data.remote.api.mapper.toCategoriesEntity
import android.project.shoppingapp.data.remote.api.mapper.toCategory
import android.project.shoppingapp.data.repository.categories.CategoriesRepository
import android.project.shoppingapp.data.repository.products.ProductRepository
import android.project.shoppingapp.utils.Resources
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CategoriesRepositoryImpl @Inject constructor(
    private val api: CategoriesAPI, private val appDatabase: AppDatabase
) : CategoriesRepository {

    private val categoryDao = appDatabase.categoriesDao()

    override fun getAllCategories(): Flow<Resources<List<Category>>> = flow {
        emit(Resources.Loading<List<Category>>(true))

        val localListings = categoryDao.getAllCategories()

        emit(Resources.Success<List<Category>>(
            data = localListings.map { it.toCategory() }
        ))

        val remoteCategories = try {
            api.getAllCategories()
        } catch (e: IOException) {
            e.printStackTrace()
            emit(Resources.Error<List<Category>>(e.message.toString()))
            null
        } catch (e: HttpException) {
            e.printStackTrace()
            emit(Resources.Error<List<Category>>(e.message()))
            null
        }

        remoteCategories?.let { categories ->
            categories.add(0, "All")
            categoryDao.deleteAll()
            categoryDao.insertCategory(
                categories.map {
                    toCategoriesEntity(it)
                }
            )
        }


        emit(Resources.Success<List<Category>>(data = categoryDao.getAllCategories().map {
            it.toCategory()
        }))
    }.catch { error ->
        Log.d("CATEGORIES", error.toString())
        emit(Resources.Error<List<Category>>(error.message.toString()))
    }.flowOn(Dispatchers.IO)


    override fun getProductsByCategory(category: String): Flow<Resources<List<ProductsDTOItem>>> =
        flow {
            emit(Resources.Loading<List<ProductsDTOItem>>(true))
            val products = api.getProductsByCategory(category)
            emit(Resources.Success<List<ProductsDTOItem>>(data = products))
        }.catch { error ->
            emit(Resources.Error<List<ProductsDTOItem>>(error.message.toString()))
        }.flowOn(Dispatchers.IO)


}