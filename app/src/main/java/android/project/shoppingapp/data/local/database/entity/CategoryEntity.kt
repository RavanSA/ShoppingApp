package android.project.shoppingapp.data.local.database.entity

import android.project.shoppingapp.utils.Constants
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = Constants.TABLE_CATEGORIES)
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    val id: Int,
    @ColumnInfo(name="category")
    val categories: String
)