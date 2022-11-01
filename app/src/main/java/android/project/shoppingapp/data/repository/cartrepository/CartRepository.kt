package android.project.shoppingapp.data.repository.cartrepository

import android.project.shoppingapp.data.local.database.entity.BasketEntity
import kotlinx.coroutines.flow.Flow

interface CartRepository {

    suspend fun insertProductToBasket(product: BasketEntity)

    suspend fun increaseProductQuantity(productId: Int)

    suspend fun decreaseProductQuantity(productId: Int)

    fun getProductQuantity(productId: Int) : Flow<Int?>

    fun getAllProductsFromBasketByUserId() : Flow<List<BasketEntity>>

    suspend fun deleteBasketItemById(productId: Int)

}