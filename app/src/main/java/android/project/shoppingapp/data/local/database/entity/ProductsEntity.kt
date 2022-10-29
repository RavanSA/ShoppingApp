package android.project.shoppingapp.data.local.database.entity

import android.project.shoppingapp.data.remote.api.dto.products.ProductsRating
import android.project.shoppingapp.utils.Constants
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = Constants.TABLE_PRODUCT)
data class ProductsEntity(
    @ColumnInfo(name="category")
    val category: String,
    @ColumnInfo(name="description")
    val description: String,
    @PrimaryKey
    @ColumnInfo(name="id")
    val id: Int,
    @ColumnInfo(name="image")
    val image: String,
    @ColumnInfo(name="price")
    val price: Double,
    @ColumnInfo(name="ratingCount")
    val ratingCount: Int,
    @ColumnInfo(name = "ratingRate")
    val ratingRate: Double,
    @ColumnInfo(name="title")
    val title: String
)
