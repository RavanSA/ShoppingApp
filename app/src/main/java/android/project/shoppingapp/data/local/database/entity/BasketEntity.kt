package android.project.shoppingapp.data.local.database.entity

import android.project.shoppingapp.utils.Constants
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = Constants.TABLE_BASKET)
data class BasketEntity(
    @ColumnInfo(name="productCategory")
    val category: String,
    @ColumnInfo(name="productDescription")
    val description: String,
    @PrimaryKey
    @ColumnInfo(name="productId")
    val productId: Int,
    @ColumnInfo(name="productImage")
    val image: String,
    @ColumnInfo(name="productPrice")
    val price: Double,
    @ColumnInfo(name="productRatingCount")
    val ratingCount: Int,
    @ColumnInfo(name = "productRatingRate")
    val ratingRate: Double,
    @ColumnInfo(name="productTitle")
    val title: String,
    @ColumnInfo(name="productQuantity")
    val productQuantity: Int,
    @ColumnInfo(name = "userID")
    val userId: String
)