package android.project.shoppingapp.data.local.database.dao

import android.project.shoppingapp.data.local.database.entity.CategoryEntity
import android.project.shoppingapp.data.local.database.entity.ProductsEntity
import android.project.shoppingapp.utils.Constants
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: CategoryEntity)

    @Query("DELETE FROM ${Constants.TABLE_CATEGORIES}")
    suspend fun deleteAll()


    @Query("SELECT * FROM ${Constants.TABLE_CATEGORIES}")
    fun getAllCategories(): Flow<List<CategoryEntity>>

}