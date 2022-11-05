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

    @Query("""UPDATE ${Constants.TABLE_BASKET}
              SET productQuantity = productQuantity + 1 
              WHERE productId = :productId AND userId=:userId
              """)
    suspend fun addProductQuantity(productId: Int, userId: String)

    @Query("""UPDATE ${Constants.TABLE_BASKET} 
              SET productQuantity = productQuantity - 1 
              WHERE productId = :productId AND userId =:userId
              """)
    suspend fun decreaseProductQuantity(productId: Int, userId: String)

    @Query("""SELECT productQuantity 
              FROM ${Constants.TABLE_BASKET} 
              WHERE productId = :productId AND userId = :userId
              """)
    fun getProductQuantity(productId: Int, userId: String) : Flow<Int?>

    @Query("""UPDATE ${Constants.TABLE_BASKET}
              SET productQuantity = :quantity
              WHERE productId = :productId AND userId = :userId""")
    suspend fun updateProductQuantity(quantity: Int, productId: String, userId: String)

    @Query("SELECT * FROM ${Constants.TABLE_BASKET} WHERE userId = :userId")
    fun getAllProductsFromBasketByUserId(userId: String) : Flow<List<BasketEntity>>

    @Query("DELETE FROM ${Constants.TABLE_BASKET} WHERE productId = :productId and userId = :userId")
    suspend fun deleteBasketItemById(productId: Int, userId: String)

    @Query("DELETE FROM ${Constants.TABLE_BASKET} WHERE userId= :userId")
    suspend fun deleteBasket(userId: String)

}