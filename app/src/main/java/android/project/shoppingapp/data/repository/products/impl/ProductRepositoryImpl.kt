package android.project.shoppingapp.data.repository.products.impl

import android.project.assignmentweek5.data.local.database.AppDatabase
import android.project.shoppingapp.data.model.Products
import android.project.shoppingapp.data.remote.api.ProductsAPI
import android.project.shoppingapp.data.remote.api.mapper.toProducts
import android.project.shoppingapp.data.remote.api.mapper.toProductsEntity
import android.project.shoppingapp.data.repository.products.ProductRepository
import android.project.shoppingapp.utils.Resources
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


class ProductRepositoryImpl @Inject constructor(
    private val api: ProductsAPI,
    private val appDatabase: AppDatabase
) : ProductRepository {

    private val productDao = appDatabase.productDao()

    override fun getProducts(
        fetchProductListFromRemote: Boolean
    ): Flow<Resources<List<Products>>?> = flow {

        emit(Resources.Loading<List<Products>>(true))

        val localProductList = productDao.getAllProducts()

        emit(Resources.Success<List<Products>>(
            data = localProductList.map { it.toProducts() }
        ))

        val isDbEmpty = localProductList.isEmpty()
        val shouldJustLoadFromCache = !isDbEmpty && !fetchProductListFromRemote

        if (shouldJustLoadFromCache) {
            return@flow
        }

        val remoteProductList = try {
            api.getAllProducts()
        } catch (e: IOException) {
            e.printStackTrace()
            emit(e.message?.let { Resources.Error<List<Products>>(it) })
            null
        } catch (e: HttpException) {
            e.printStackTrace()
            emit(e.message?.let { Resources.Error<List<Products>>(it) })
            null
        }

        remoteProductList?.let { products ->
            productDao.clearProductsCache()
            productDao.insertProducts(
                products.map { it.toProductsEntity() }
            )
        }

        emit(Resources.Success<List<Products>>(data =
        productDao.getAllProducts().map { it.toProducts() }
        ))
    }.catch { error ->
        emit(Resources.Error<List<Products>>(error.message.toString()))
    }.flowOn(Dispatchers.IO)


    override fun getProductsBySearchFilters(
        category: String?,
        searchQuery: String
    ): Flow<List<Products>> {
        return productDao.getProductsdBySearchFilters(
            category = category,
            searchQuery = searchQuery
        )
    }

    override fun getProductById(productId: Int): Flow<Resources<Products>> = flow {
        emit(Resources.Loading<Products>(true))

        val products = api.getProduct(productId)

        emit(Resources.Success<Products>(
            data = products.toProducts()
        )
        )
    }.catch { error ->
        emit(Resources.Error<Products>(error.message.toString()))
    }.flowOn(Dispatchers.IO)


}