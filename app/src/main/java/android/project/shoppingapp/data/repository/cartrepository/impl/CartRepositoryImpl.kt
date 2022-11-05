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

    override suspend fun increaseProductQuantity(productId: Int, userId: String) {
        return dao.addProductQuantity(productId, userId)
    }

    override suspend fun decreaseProductQuantity(productId: Int, userId: String) {
        return dao.decreaseProductQuantity(productId, userId)
    }

    override fun getProductQuantity(productId: Int, userId: String): Flow<Int?> {
        return dao.getProductQuantity(productId, userId)
    }

    override fun getAllProductsFromBasketByUserId(userId: String): Flow<List<BasketEntity>> {
        return dao.getAllProductsFromBasketByUserId(userId)
    }

    override suspend fun deleteBasketItemById(productId: Int, userId: String) {
        return dao.deleteBasketItemById(productId, userId)
    }

    override suspend fun deleteBasket(userId: String) {
        return dao.deleteBasket(userId)
    }

}