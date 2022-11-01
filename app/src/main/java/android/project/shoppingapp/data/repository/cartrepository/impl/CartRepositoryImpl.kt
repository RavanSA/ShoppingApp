package android.project.shoppingapp.data.repository.cartrepository.impl

import android.project.assignmentweek5.data.local.database.AppDatabase
import android.project.shoppingapp.data.local.database.entity.BasketEntity
import android.project.shoppingapp.data.repository.cartrepository.CartRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val appDatabase: AppDatabase
) : CartRepository {

    private val dao = appDatabase.basketDao()

    override suspend fun insertProductToBasket(product: BasketEntity) {
        return dao.insertProductToBasket(product)
    }

    override suspend fun increaseProductQuantity(productId: Int) {
        return dao.addProductQuantity(productId)
    }

    override suspend fun decreaseProductQuantity(productId: Int) {
        return dao.decreaseProductQuantity(productId)
    }

    override fun getProductQuantity(productId: Int): Flow<Int?> {
        return dao.getProductQuantity(productId)
    }

    override fun getAllProductsFromBasketByUserId(): Flow<List<BasketEntity>> {
        return dao.getAllProductsFromBasketByUserId()
    }

    override suspend fun deleteBasketItemById(productId: Int) {
        return dao.deleteBasketItemById(productId)
    }

}