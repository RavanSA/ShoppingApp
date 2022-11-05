package android.project.shoppingapp.data.repository.cartrepository

import android.project.shoppingapp.data.local.database.entity.BasketEntity
import kotlinx.coroutines.flow.Flow

interface CartRepository {

    suspend fun insertProductToBasket(product: BasketEntity)

    suspend fun increaseProductQuantity(productId: Int, userId: String)

    suspend fun decreaseProductQuantity(productId: Int, userId: String)

    fun getProductQuantity(productId: Int, userId: String) : Flow<Int?>

    fun getAllProductsFromBasketByUserId(userId: String) : Flow<List<BasketEntity>>

    suspend fun deleteBasketItemById(productId: Int, userId: String)

    suspend fun deleteBasket(userId: String)


}