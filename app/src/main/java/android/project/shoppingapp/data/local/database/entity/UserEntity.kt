package android.project.shoppingapp.data.local.database.entity

import android.project.shoppingapp.utils.Constants
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = Constants.TABLE_USER)
data class UserEntity(
    val username: String,
    val email: String,
    @PrimaryKey
    val uid: String
)