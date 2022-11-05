package android.project.shoppingapp.data.local.database.dao

import android.project.shoppingapp.data.local.database.entity.BasketEntity
import android.project.shoppingapp.utils.Constants
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface BasketDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProductToBasket(product: BasketEntity)

    @Query("UPDATE ${Constants.TABLE_BASKET} SET productQuantity = productQuantity + 1 WHERE productId = :productId")
    suspend fun addProductQuantity(productId: Int)

    @Query("UPDATE ${Constants.TABLE_BASKET} SET productQuantity = productQuantity - 1 WHERE productId = :productId")
    suspend fun decreaseProductQuantity(productId: Int)

    @Query("SELECT productQuantity FROM ${Constants.TABLE_BASKET} WHERE productId = :productId")
    fun getProductQuantity(productId: Int) : Flow<Int?>

    @Query("UPDATE ${Constants.TABLE_BASKET} SET productQuantity = :quantity WHERE productId = :productId")
    suspend fun updateProductQuantity(quantity: Int, productId: String)

//    @Query("")
//    suspend fun getProductQuantityById()

//    @Delete
//    suspend fun removeProductsFromBasket()


    @Query("SELECT * FROM ${Constants.TABLE_BASKET}")
    fun getAllProductsFromBasketByUserId() : Flow<List<BasketEntity>>

    @Query("DELETE FROM ${Constants.TABLE_BASKET} WHERE productId = :productId")
    suspend fun deleteBasketItemById(productId: Int)


    @Query("DELETE FROM ${Constants.TABLE_BASKET}")
    suspend fun deleteBasket()

}