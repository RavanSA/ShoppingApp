package android.project.shoppingapp.data.local.database.dao

import android.project.assignmentweek5.data.local.database.base.BaseDao
import android.project.shoppingapp.data.local.database.entity.ProductsEntity
import android.project.shoppingapp.data.remote.api.dto.products.ProductDTO
import android.project.shoppingapp.utils.Constants
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductsDao : BaseDao<ProductsEntity> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
   suspend fun insertProduct(product: ProductsEntity)

    @Query("SELECT * FROM ${Constants.TABLE_PRODUCT}")
    fun getAllProducts(): Flow<List<ProductsEntity>>

    @Query("DELETE FROM ${Constants.TABLE_PRODUCT}")
    suspend fun deleteAll()

    @Query("SELECT * FROM ${Constants.TABLE_PRODUCT} WHERE  id = :productId")
    fun getProductById(productId: Int): Flow<ProductsEntity?>

}