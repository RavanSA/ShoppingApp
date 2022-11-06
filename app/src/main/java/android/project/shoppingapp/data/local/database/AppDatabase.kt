package android.project.shoppingapp.data.local.database

import android.content.Context
import android.project.shoppingapp.data.local.database.dao.BasketDao
import android.project.shoppingapp.data.local.database.dao.CategoryDao
import android.project.shoppingapp.data.local.database.dao.ProductsDao
import android.project.shoppingapp.data.local.database.dao.UserDao
import android.project.shoppingapp.data.local.database.entity.BasketEntity
import android.project.shoppingapp.data.local.database.entity.CategoryEntity
import android.project.shoppingapp.data.local.database.entity.ProductsEntity
import android.project.shoppingapp.data.local.database.entity.UserEntity
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ProductsEntity::class, UserEntity::class,
    CategoryEntity::class, BasketEntity::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductsDao
    abstract fun categoriesDao(): CategoryDao
    abstract fun basketDao(): BasketDao
    abstract fun userDao(): UserDao


    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase =
            instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }

        private fun buildDatabase(appContext: Context) =
            Room.databaseBuilder(appContext, AppDatabase::class.java, "AppDatabase")
                .fallbackToDestructiveMigration()
                .build()
    }

}