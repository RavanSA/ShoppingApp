package android.project.shoppingapp.data.di

import android.content.Context
import android.project.assignmentweek5.data.local.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDataModule {


    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return AppDatabase.getDatabase(appContext)
    }

    @Singleton
    @Provides
    fun provideProductDao(db: AppDatabase) = db.productDao()

}