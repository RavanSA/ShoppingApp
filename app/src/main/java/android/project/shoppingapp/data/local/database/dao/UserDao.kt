package android.project.shoppingapp.data.local.database.dao

import android.project.shoppingapp.data.local.database.entity.UserEntity
import android.project.shoppingapp.utils.Constants
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Query("UPDATE ${Constants.TABLE_USER} SET username =:userName AND email =:email WHERE uid =:userId")
    suspend fun updateUserInfo(userName: String, email: String, userId: String)

}