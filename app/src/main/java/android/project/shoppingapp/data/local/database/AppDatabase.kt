package android.project.assignmentweek5.data.local.database

import android.content.Context
import android.project.shoppingapp.data.local.database.dao.CategoryDao
import android.project.shoppingapp.data.local.database.dao.ProductsDao
import android.project.shoppingapp.data.local.database.entity.CategoryEntity
import android.project.shoppingapp.data.local.database.entity.ProductsEntity
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ProductsEntity::class, CategoryEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductsDao
    abstract fun categoriesDao(): CategoryDao

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